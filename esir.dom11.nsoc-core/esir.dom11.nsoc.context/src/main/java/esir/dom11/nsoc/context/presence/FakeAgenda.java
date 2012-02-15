package esir.dom11.nsoc.context.presence;

import esir.dom11.nsoc.context.calendar.Calendar;
import esir.dom11.nsoc.context.calendar.CalendarEvent;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.util.Date;
import java.util.LinkedList;

@Requires({
        @RequiredPort(name = "fakeAgenda", type = PortType.MESSAGE)
})
@Library(name = "NSOC_2011::Context")
@ComponentType
public class FakeAgenda extends AbstractComponentType {

    @Start
    public void start() {
        update();
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {
        sendAgenda(generateAgenda());
    }

    public Calendar generateAgenda() {
        long currentTime = new Date().getTime();
        LinkedList<CalendarEvent> events = new LinkedList<CalendarEvent>();
        events.add(
                new CalendarEvent(
                        new Date(currentTime + 2000),
                        new Date(currentTime + 6000)
                )
        );
        events.add(
                new CalendarEvent(
                        new Date(currentTime + 12000),
                        new Date(currentTime + 18000)
                )
        );


        Calendar calendar = new Calendar();
        calendar.getEvents().addAll(events);
        return calendar;
    }


    public void sendAgenda(Calendar calendar) {
        if (this.isPortBinded("fakeAgenda")) {
            this.getPortByName("fakeAgenda", MessagePort.class).process(calendar);
        }
    }

}
