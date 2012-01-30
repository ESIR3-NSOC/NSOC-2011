package esir.dom11.nsoc.model;

import java.util.Date;
import java.util.UUID;

public class Data implements Comparable<Data> {

    /*
     * Attributes
     */

    private final UUID _id;             // dao key
    private final UUID _idSensor;
    private final double _value;
    private final Date _date;

    /*
    * Constructors
    */

    public Data() {
        // default constructor
        _id = UUID.fromString("00000000-0000-0000-0000-000000000000");
        _idSensor = UUID.fromString("00000000-0000-0000-0000-000000000000");
        _value = 0.0;
        _date = new Date();
    }

    public Data(UUID idSensor, double value, Date date) {
        _id = UUID.randomUUID();
        _idSensor = idSensor;
        _value = value;
        _date = date;
    }

    public Data(UUID id, UUID idSensor, double value, Date date) {
        _id = id;
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

    public UUID getIdSensor() {
        return _idSensor;
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
                + "\nId Sensor: " + getIdSensor()
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
