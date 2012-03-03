package test;

import esir.dom11.nsoc.context.calendar.Calendar;
import esir.dom11.nsoc.context.calendar.CalendarEvent;
import esir.dom11.nsoc.context.presence.PresenceEvent;
import esir.dom11.nsoc.context.presence.PresenceListener;
import esir.dom11.nsoc.context.presence.PresenceManager;
import junit.framework.TestCase;

import java.util.Date;
import java.util.LinkedList;

public class ContextPresenceTest extends TestCase {

    public void test() {


        PresenceManager presMan = new PresenceManager();
        presMan.addPresenceEventListener(new PresenceListener() {

            @Override
            public void sendCalendar(Calendar calendar) {
                System.out.println("\n--new Calendar--");
                for (CalendarEvent event : calendar.getEvents()) {
                    System.out.println(event);
                }
            }
        });
        LinkedList<CalendarEvent> events = new LinkedList<CalendarEvent>();
        Date now = new Date();
        events.add(new CalendarEvent(new Date(now.getTime() + 2000),
                new Date(now.getTime() + 4000)));
        presMan.setCalendar(events);

        tempo(2000);
        // cancel , uncomment to generate confirmation
        //  presMan.getCepRT().sendEvent(new PresenceEvent("1", true));
        tempo(2000);
        presMan.getCepRT().sendEvent(new PresenceEvent("1", false));
        tempo(2000);
        presMan.getCepRT().sendEvent(new PresenceEvent("1", true));
        tempo(2000);
        presMan.getCepRT().sendEvent(new PresenceEvent("1", false));
        tempo(2000);
    }

    public void tempo(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
