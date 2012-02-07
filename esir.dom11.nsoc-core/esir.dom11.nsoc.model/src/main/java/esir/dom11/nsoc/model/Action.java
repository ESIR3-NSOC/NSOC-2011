package esir.dom11.nsoc.model;

import esir.dom11.nsoc.model.device.Actuator;

import java.util.UUID;

public class Action {

    /*
     * Attributes
     */

    private UUID _id;           //  dao key
    private Actuator _actuator;
    private Object _value;

    /*
     * Constructors
     */
    
    public Action() {
        _id = UUID.randomUUID();
    }
    
    public Action(Actuator actuator, Object value) {
        _id = UUID.randomUUID();
        _actuator = actuator;
        _value = value;
    }

    public Action(UUID id, Actuator actuator, Object value) {
        _id = id;
        _actuator = actuator;
        _value = value;
    }

    /*
     * Getters / Setters
     */

    public UUID getId() {
        return _id;
    }

    public Actuator getActuator() {
        return _actuator;
    }

    public void setIdActuator(Actuator actuator) {
        _actuator = actuator;
    }

    public Object getValue() {
        return _value;
    }

    public void setValue(Object value) {
        _value = value;
    }

    /*
    * Overrides
    */

    @Override
    public String toString() {
        return "\n* * * Action " + getId() + " * * *"
                + "\nActuator: " + getActuator()
                + "\nValue: " + getValue();
    }

    /*
     * Methods
     */
}
