package esir.dom11.nsoc.model;

import java.util.Date;
import java.util.UUID;

public class Data implements Comparable<Data> {

    /*
     * Attribute
     */

    private UUID _id;           // dao key
    private DataType _dataType;
    private UUID _idSensor;
    private double _value;
    private Date _date;
    
    /*
     * Constructors
     */
    
    public Data() {}

    public Data(DataType dataType, UUID idSensor, double value, Date date) {
        _id = UUID.randomUUID();
        _dataType = dataType;
        _idSensor = idSensor;
        _value = value;
        _date = date;
    }
    
    public Data(UUID id, DataType dataType, UUID idSensor, double value, Date date) {
        _id = id;
        _dataType = dataType;
        _idSensor = idSensor;
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

    public UUID getIdSensor() {
        return _idSensor;
    }

    public void setIdSensor(UUID idSensor) {
        _idSensor = idSensor;
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
        return "\n* * * Data " + getId() + " * * *"
                + "\nData Type: " + getDataType().getValue()
                + "\nValue: " + getValue()
                + "\nSensor: " + getIdSensor()
                + "\nDate: " + getDate() + "\n";
    }

    @Override
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
