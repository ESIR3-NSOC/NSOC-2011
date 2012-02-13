package esir.dom11.nsoc.model;

import java.util.Date;
import java.util.LinkedList;

public class Agenda {

    private LinkedList<AgendaEvent> events;

    public Agenda(){
        events = new LinkedList<AgendaEvent>();
    }

    public LinkedList<AgendaEvent> getEvents() {
        return events;
    }

    public AgendaEvent getEventByDate(Date date){
        // TODO
        return null;
    }
}
