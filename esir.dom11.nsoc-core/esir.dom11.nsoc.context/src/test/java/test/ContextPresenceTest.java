package test;

import esir.dom11.nsoc.context.presence.*;
import junit.framework.TestCase;

import java.util.Date;
import java.util.LinkedList;

public class ContextPresenceTest extends TestCase {

    public void test() {


        PresenceManager presMan = new PresenceManager();
        presMan.addPresenceEventListener(new PresenceListener() {

            @Override
            public void sendAgenda(Agenda agenda) {
                System.out.println("\n--new Agenda--");
                for (AgendaEvent event : agenda.getEvents()) {
                    System.out.println(event);
                }
            }
        });
        LinkedList<AgendaEvent> events = new LinkedList<AgendaEvent>();
        Date now = new Date();
        events.add(new AgendaEvent(new Date(now.getTime() + 2000),
                new Date(now.getTime() + 4000)));
        presMan.setAgenda(events);

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
