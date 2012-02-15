package esir.dom11.nsoc.context.calendar;

import java.util.Date;
import java.util.LinkedList;

public class Calendar {

    private LinkedList<CalendarEvent> events;

    public Calendar() {
        events = new LinkedList<CalendarEvent>();
    }

    public LinkedList<CalendarEvent> getEvents() {
        return events;
    }

    public CalendarEvent getEventByDate(Date date) {
        for (CalendarEvent event : events) {
            if (date.after(event.getDateStart())
                    && date.before(event.getDateEnd())) {
                return event;
            }
        }
        return null;
    }

    public String toString() {
        String str = "[ Calendar \n";
        for (CalendarEvent event : events) {
            str += event.toString() + "\n";
        }
        str += "]\n";
        return str;
    }
}
