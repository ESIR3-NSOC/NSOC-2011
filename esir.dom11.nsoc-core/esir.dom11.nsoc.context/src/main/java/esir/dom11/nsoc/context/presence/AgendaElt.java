package esir.dom11.nsoc.context.presence;

import java.util.Date;

public class AgendaElt {
    private final String _name;
    private final Date _begin;
    private final Date _end;

    public AgendaElt() {
        _name = "";
        _begin = new Date();
        _end = new Date();
    }

    public AgendaElt(String name, Date begin, Date end) {
        _name = name;
        _begin = begin;
        _end = end;
    }

    public String getName() {
        return _name;
    }

    public Date getBegin() {
        return _begin;
    }

    public Date getEnd() {
        return _end;
    }
}
