package esir.dom11.nsoc.context.presence;

import esir.dom11.nsoc.context.calendar.Calendar;
import esir.dom11.nsoc.context.calendar.CalendarEvent;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.util.Date;
import java.util.LinkedList;

@Requires({
        @RequiredPort(name = "fakecalendar", type = PortType.MESSAGE)
})
@Library(name = "NSOC_2011::Context")
@ComponentType
public class FakeCalendar extends AbstractComponentType {

    @Start
    public void start() {
        update();
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {
        sendCalendar(generateCalendar());
    }

    public Calendar generateCalendar() {
        long currentTime = new Date().getTime();
        LinkedList<CalendarEvent> events = new LinkedList<CalendarEvent>();
        events.add(
                new CalendarEvent(
                        new Date(currentTime + 5000),
                        new Date(currentTime + 15000)
                )
        );
        events.add(
                new CalendarEvent(
                        new Date(currentTime + 35000),
                        new Date(currentTime + 40000)
                )
        );
        Calendar calendar = new Calendar();
        calendar.getEvents().addAll(events);
        return calendar;
    }


    public void sendCalendar(Calendar calendar) {
        System.out.println("Context::FakeCalendar : sendCalendar");
        if (this.isPortBinded("fakeCalendar")) {
            this.getPortByName("fakeCalendar", MessagePort.class).process(calendar);
        }
    }

}
