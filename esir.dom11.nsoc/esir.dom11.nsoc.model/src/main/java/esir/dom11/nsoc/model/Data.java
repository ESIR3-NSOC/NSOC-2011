package esir.dom11.nsoc.model;

import java.util.Date;
import java.util.UUID;

public class Data {

    /*
     * Attribute
     */

    private UUID _id;           // dao key
    private DataType _dataType;
    private String _role;     // example : "temp-int-salle930"
    private double _value;
    private Date _date;

    /*
    * Constructors
    */

    public Data() {
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

    public void setDataType(DataType dataType) {
        _dataType = dataType;
    }

    public String getRole() {
        return _role;
    }

    public void setRole(String role) {
        _role = role;
    }

    public double getValue() {
        return _value;
    }

    public void setValue(double value) {
        _value = value;
    }

    public Date getDate() {
        return _date;
    }

    public void setDate(Date date) {
        _date = date;
    }

    /*
    * Overrides
    */

    @Override
    public String toString() {
        return "\n* * * Task " + getId() + " * * *"
                + "\nData Type: " + getDataType().getValue()
                + "\nRole: " + getRole()
                + "\nDate: " + getDate() + "\n";
    }

    /*
     * Methods
     */
}
