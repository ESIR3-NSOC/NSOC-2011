package test;

import esir.dom11.nsoc.context.presence.AgendaChecker;
import esir.dom11.nsoc.context.presence.AgendaEvent;
import esir.dom11.nsoc.context.presence.AgendaEventListener;
import junit.framework.TestCase;

import java.util.Date;
import java.util.LinkedList;

public class AgendaTest extends TestCase {

    public void testAgenda() {

        long currentTime = new Date().getTime();
        LinkedList<AgendaEvent> events = new LinkedList<AgendaEvent>();
        events.add(
                new AgendaEvent(
                        new Date(currentTime + 3000),
                        new Date(currentTime + 6000)
                )
        );

        AgendaChecker agendaChecker = new AgendaChecker(events);
        agendaChecker.addAgendaEventListener(new AgendaEventListener() {
            @Override
            public void eventStart() {
                System.out.println("start");
            }

            @Override
            public void eventStop() {
                System.out.println("stop");
            }
        });

        agendaChecker.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        agendaChecker.setActive(false);
    }

}
