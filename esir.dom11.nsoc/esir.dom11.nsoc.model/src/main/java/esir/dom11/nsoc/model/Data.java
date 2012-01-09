package esir.dom11.nsoc.model;

import java.util.Date;
import java.util.UUID;

public final class Data implements Comparable<Data> {

    /*
     * Attributes
     */

    private final UUID _id;           // dao key
    private final DataType _dataType;
    private final String _role;     // example : "temp-int-salle930"
    private final double _value;
    private final Date _date;

    /*
    * Constructors
    */

    public Data() {
        // default constructor
        _id = UUID.randomUUID();
        _dataType = DataType.TEMPERATURE;
        _role = "";
        _value = 0.0;
        _date = new Date();
    }

    public Data(DataType dataType, String role, double value, Date date) {
        _id = UUID.randomUUID();
        _dataType = dataType;
        _role = role;
        _value = value;
        _date = date;
    }

    public Data(UUID id, DataType dataType, String role, double value, Date date) {
        _id = id;
        _dataType = dataType;
        _role = role;
        _value = value;
        _date = date;
    }

    /*
     * Getters / Setters
     */

    public UUID getId() {
        return _id;
    }

    public DataType getDataType() {
        return _dataType;
    }

    public String getRole() {
        return _role;
    }

    public double getValue() {
        return _value;
    }

    public Date getDate() {
        return _date;
    }

    /*
    * Overrides
    */

    @Override
    public String toString() {
        return "\n* * * Data " + getId() + " * * *"
                + "\nData Type: " + getDataType().getValue()
                + "\nRole: " + getRole()
                + "\nValue: " + getValue()
                + "\nDate: " + getDate() + "\n";
    }

    public int compareTo(Data data) {
        if ( data.getValue() > getValue() ) {
            return 1;
        } else if ( data.getValue() < getValue() ) {
            return -1;
        }
        return 0;
    }

    /*
     * Methods
     */


}
