package esir.dom11.nsoc.context.presence;

import com.espertech.esper.client.*;
import esir.dom11.nsoc.context.calendar.Calendar;
import esir.dom11.nsoc.context.calendar.CalendarEvent;

import javax.swing.event.EventListenerList;
import java.util.Date;
import java.util.LinkedList;

public class PresenceManager implements PresenceListener {

    private boolean presence = false;
    private EPRuntime cepRT;
    private EPServiceProvider cep;
    private EPStatement var_presence;
    private EPStatement var_presence_true;
    private EPStatement var_presence_false;
    private EPStatement confirmation;
    private EPStatement newPresence;
    private EPStatement endPresence;
    private EPStatement cancel;
    protected EventListenerList listenerList;
    private CalendarChecker calendarChecker;

    public PresenceManager() {
        this.listenerList = new EventListenerList();

        calendarChecker = new CalendarChecker();
        calendarChecker.addAgendaEventListener(new CalendarCheckerListener() {
            @Override
            public void eventStart() {
                getCepRT().sendEvent(new PresenceAgendaEvent("salle", true));
                System.out.println("Context::PresenceComp : start agenda event");
            }

            @Override
            public void eventStop() {
                getCepRT().sendEvent(new PresenceAgendaEvent("salle", false));
                System.out.println("Context::PresenceComp : stop agenda event");
            }
        });

        Configuration cepConfig = new Configuration();
        cepConfig.addEventType("PresenceEvent", "esir.dom11.nsoc.context.presence.PresenceEvent");
        cepConfig.addEventType("PresenceAgendaEvent", "esir.dom11.nsoc.context.presence.PresenceAgendaEvent");
        cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
        cepRT = cep.getEPRuntime();

        EPAdministrator cepAdm = cep.getEPAdministrator();
        var_presence = cepAdm.createEPL("create variable boolean var_presence = false");
        var_presence.start();
        var_presence_true = cepAdm.createEPL("select * from pattern[every PresenceEvent(presence=true)]");
        var_presence_false = cepAdm.createEPL("select * from pattern[every PresenceEvent(presence=false)]");
        var_presence_true.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData) {
                presence = true;
            }
        });
        var_presence_false.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData) {
                presence = false;
            }
        });

        String confirmation_startWindow = "1 sec";
        String confirmation_minDuration = "1 sec";
        confirmation = cepAdm.createEPL("select * from pattern[" +
                "every (PresenceAgendaEvent(presence=true) and PresenceEvent(presence=true))where timer:within(" + confirmation_startWindow + ") " +
                "->  timer:interval(" + confirmation_minDuration + ") and not PresenceEvent(presence=false) ]");

        newPresence = cepAdm.createEPL("select * from pattern" +
                "[every (" +
                "( (PresenceEvent(presence=true) and not PresenceAgendaEvent(presence=true)) where timer:within(1 sec) " +
                "-> timer:interval(1 sec) and not PresenceEvent(presence=false) )" +
                ")]");

        endPresence = cepAdm.createEPL("select * from pattern[" +
                "every PresenceEvent(presence=false) " +
                "-> timer:interval(1 sec) and not PresenceEvent(presence=true) ]");

        cancel = cepAdm.createEPL("select * from pattern[" +
                "every PresenceAgendaEvent(presence=true) " +
                "-> timer:interval(1 sec) and not PresenceEvent(presence=true) ]");


        confirmation.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData) {
                System.out.println("Context::PresenceComp : confirmation agenda event");
            }
        });
        cancel.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData) {
                if (!presence) {
                    calendarChecker.getCalendar().getEvents().remove(
                            calendarChecker.getCalendar().getEventByDate(new Date())
                    );
                    sendAgenda(calendarChecker.getCalendar());
                    System.out.println("Context::PresenceComp : cancel agenda event");
                }
            }
        });
        newPresence.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData) {
                //
                Date now = new Date();
                calendarChecker.getCalendar().getEvents().add(
                        new CalendarEvent(now,
                                new Date(now.getTime() + 900000) // 15 min
                        )
                );
                sendAgenda(calendarChecker.getCalendar());
                System.out.println("Context::PresenceComp : new presence");
            }
        });
        endPresence.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData) {
                System.out.println("Context::PresenceComp : end presence");
            }
        });

        calendarChecker.start();
    }

    public void setAgenda(LinkedList<CalendarEvent> events) {
        calendarChecker.getCalendar().getEvents().clear();
        calendarChecker.getCalendar().getEvents().addAll(events);
    }

    public void stop() {
        var_presence.removeAllListeners();
        var_presence_true.removeAllListeners();
        var_presence_false.removeAllListeners();
        confirmation.removeAllListeners();
        newPresence.removeAllListeners();
        endPresence.removeAllListeners();
        cancel.removeAllListeners();

        cep.destroy();
    }

    public EPRuntime getCepRT() {
        return cepRT;
    }


    public void addPresenceEventListener(PresenceListener l) {
        this.listenerList.add(PresenceListener.class, l);
    }

    @Override
    public void sendAgenda(Calendar calendar) {
        PresenceListener[] listeners = (PresenceListener[])
                listenerList.getListeners(PresenceListener.class);
        for (int i = listeners.length - 1; i >= 0; i--) {
            listeners[i].sendAgenda(calendar);
        }
    }
}
