package esir.dom11.nsoc.model;

import esir.dom11.nsoc.model.device.Device;
import esir.dom11.nsoc.model.device.Sensor;

import java.util.Date;
import java.util.UUID;

public class Data {

    /*
     * Attributes
     */

    private final UUID _id;             // dao key
    private final Sensor _sensor;
    private final String _value;
    private final Date _date;

    /*
    * Constructors
    */

    public Data() {
        // default constructor
        _id = UUID.fromString("00000000-0000-0000-0000-000000000000");
        _sensor = new Sensor();
        _value = "0.0";
        _date = new Date();
    }

    public Data(Sensor sensor, String value, Date date) {
        _id = UUID.randomUUID();
        _sensor = sensor;
        _value = value;
        _date = date;
    }

    public Data(UUID id, Sensor sensor, String value, Date date) {
        _id = id;
        _sensor = sensor;
        _value = value;
        _date = date;
    }

    /*
     * Getters / Setters
     */

    public UUID getId() {
        return _id;
    }

    public Device getSensor() {
        return _sensor;
    }

    public String getValue() {
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
                + "\nSensor: " + getSensor()
                + "\nValue: " + getValue()
                + "\nDate: " + getDate() + "\n";
    }

    public String serialized(){
        return "data:"+getId()+",sensor:"+getSensor()+",value:"+getValue()+",date:"+getDate();
    }

    /*
     * Methods
     */


}
