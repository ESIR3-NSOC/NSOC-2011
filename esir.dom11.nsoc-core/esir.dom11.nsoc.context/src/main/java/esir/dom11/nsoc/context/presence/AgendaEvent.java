package esir.dom11.nsoc.context.presence;

import java.util.Date;

public class AgendaEvent {

    private final Date start;
    private final Date end;

    public AgendaEvent() {
        start = new Date();
        end = new Date();
    }

    public AgendaEvent(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
    
    public String toString(){
        return "Event " + start + " -> " + end;
    }
}
