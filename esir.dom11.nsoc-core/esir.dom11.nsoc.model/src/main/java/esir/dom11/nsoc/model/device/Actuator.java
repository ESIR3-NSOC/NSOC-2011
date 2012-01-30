package esir.dom11.nsoc.model.device;

import esir.dom11.nsoc.model.DataType;

import java.util.UUID;

public class Actuator implements Device {

    /*
    * Attributes
    */

    protected UUID _id;
    protected DataType _dataType;
    protected String _location;      // example : "/bat7/salle930/"

    /*
    * Constructors
    */

    public Actuator() {

    }

    public Actuator(DataType dataType, String location) {
        _id = UUID.randomUUID();
        _dataType = dataType;
        _location = location;
    }

    public Actuator(UUID id, DataType dataType, String location) {
        _id = id;
        _dataType = dataType;
        _location = location;
    }

    /*
     * Overrides
     */

    @Override
    public UUID getId() {
        return _id;
    }

    @Override
    public String getLocation() {
        return _location;
    }

    @Override
    public void setLocation(String location) {
        _location = location;
    }
}
