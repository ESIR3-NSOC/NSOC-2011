package test;

import esir.dom11.nsoc.context.calendar.CalendarEvent;
import esir.dom11.nsoc.context.presence.CalendarChecker;
import esir.dom11.nsoc.context.presence.CalendarCheckerListener;
import junit.framework.TestCase;

import java.util.Date;
import java.util.LinkedList;

public class CalendarCheckerTest extends TestCase {

    public void testAgenda() {


        long currentTime = new Date().getTime();
        LinkedList<CalendarEvent> events = new LinkedList<CalendarEvent>();
        events.add(
                new CalendarEvent(
                        new Date(currentTime + 3000),
                        new Date(currentTime + 6000)
                )
        );

        CalendarChecker calendarChecker = new CalendarChecker();
        calendarChecker.getCalendar().getEvents().addAll(events);
        calendarChecker.addAgendaEventListener(new CalendarCheckerListener() {
            @Override
            public void eventStart() {
                System.out.println("start");
            }

            @Override
            public void eventStop() {
                System.out.println("stop");
            }
        });

        calendarChecker.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        calendarChecker.setActive(false);
    }

}
