package esir.dom11.nsoc.context.presence;

import com.espertech.esper.client.*;

import javax.swing.event.EventListenerList;

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

    public PresenceManager() {
        this.listenerList = new EventListenerList();

        Configuration cepConfig = new Configuration();
        cepConfig.addEventType("Presence", "esir.dom11.nsoc.context.presence.Presence");
        cepConfig.addEventType("PresenceAgenda", "esir.dom11.nsoc.context.presence.PresenceAgenda");
        cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
        cepRT = cep.getEPRuntime();

        EPAdministrator cepAdm = cep.getEPAdministrator();
        var_presence = cepAdm.createEPL("create variable boolean var_presence = false");
        var_presence.start();
        var_presence_true = cepAdm.createEPL("select * from pattern[every Presence(presence=true)]");
        var_presence_false = cepAdm.createEPL("select * from pattern[every Presence(presence=false)]");
        var_presence_true.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData){
                presence = true;
            }
        });
        var_presence_false.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData){
                presence = false;
            }
        });

        //  EPStatement var_agenda_presence = cepAdm.createEPL("create variable boolean var_agenda_presence = false");
        //  var_presence.start();
        String confirmation_startWindow = "1 sec";
        String confirmation_minDuration = "1 sec";
        confirmation = cepAdm.createEPL("select * from pattern[" +
                "every (PresenceAgenda(presence=true) and Presence(presence=true))where timer:within(" + confirmation_startWindow + ") " +
                "->  timer:interval(" + confirmation_minDuration + ") and not Presence(presence=false) ]");

        newPresence = cepAdm.createEPL("select * from pattern" +
                "[every (" +
                "( (Presence(presence=true) and not PresenceAgenda(presence=true)) where timer:within(1 sec) " +
                "-> timer:interval(1 sec) and not Presence(presence=true) )" +
                " or " +
                "( (PresenceAgenda(presence=false) and not Presence(presence=false)) where timer:within(1 sec) )" +
                ")]");

        endPresence = cepAdm.createEPL("select * from pattern[" +
                "every Presence(presence=false) " +
                "-> timer:interval(1 sec) and not Presence(presence=true) ]");

        cancel = cepAdm.createEPL("select * from pattern[" +
                "every PresenceAgenda(presence=true) " +
                "-> timer:interval(1 sec) and not Presence(presence=true) ]");


        confirmation.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData){
                    presenceEvent("- context - confirmation agenda presence");
            }
        });
        cancel.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData){
                if(!presence) {
                    presenceEvent("- context - cancel agenda presence");
                }
            }
        });
        newPresence.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData){
                    presenceEvent("- context - new temporary presence");
            }
        });
        endPresence.addListener(new UpdateListener() {
            @Override
            public void update(EventBean[] newData, EventBean[] oldData){
                    presenceEvent("- context - end presence");
            }
        });
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

    @Override
    public void presenceEvent(String message) {
        PresenceListener[] listeners = (PresenceListener[])
                listenerList.getListeners(PresenceListener.class);
        for (int i = listeners.length - 1; i >= 0; i--) {
            listeners[i].presenceEvent(message);
        }
    }

    public void addPresenceEventListener(PresenceListener l) {
        this.listenerList.add(PresenceListener.class, l);
    }
}
