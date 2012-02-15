package esir.dom11.nsoc.context.presence;

import java.util.Date;
import java.util.LinkedList;

public class Agenda {

    private LinkedList<AgendaEvent> events;

    public Agenda() {
        events = new LinkedList<AgendaEvent>();
    }

    public LinkedList<AgendaEvent> getEvents() {
        return events;
    }

    public AgendaEvent getEventByDate(Date date) {
        for (AgendaEvent event : events) {
            if (date.after(event.getStart())
                    && date.before(event.getEnd())) {
                return event;
            }
        }
        return null;
    }

    public String toString() {
        String str = "[ Agenda \n";
        for (AgendaEvent event : events) {
            str += event.toString() + "\n";
        }
        str += "]\n";
        return str;
    }
}
