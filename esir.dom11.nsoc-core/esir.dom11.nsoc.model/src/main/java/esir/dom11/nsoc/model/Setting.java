package esir.dom11.nsoc.model;

import java.util.Date;
import java.util.UUID;

public final class Setting {

    private final UUID _id;
    private final String _location;
    private final double _value;
    private final Date _begin;
    private final Date _end;

    public Setting() {
        _id = UUID.randomUUID();
        _location = "";
        _value = 0.0;
        _begin = new Date();
        _end = new Date();
    }

    public Setting(String role, double value, Date begin, Date end) {
        _id = UUID.randomUUID();
        _location = role;
        _value = value;
        _begin = begin;
        _end = end;
    }

    public UUID getId() {
        return _id;
    }

    public String getRole() {
        return _location;
    }

    public double getValue() {
        return _value;
    }

    public Date getBeginDate() {
        return _begin;
    }

    public Date getEndDate() {
        return _end;
    }

    public String toString() {
        return "\n* * * Setting " + getId() + " * * *"
                + "\nRole: " + getRole()
                + "\nValue: " + getValue()
                + "\nBegin Date: " + getBeginDate()
                + "\nEnd Date: " + getEndDate() + "\n";
    }

}
