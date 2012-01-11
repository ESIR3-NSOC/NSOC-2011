package esir.dom11.nsoc.model;

import java.util.UUID;

public class Action {

    /*
     * Attributes
     */

    private UUID _id;           //  dao key
    private UUID _idActuator;
    private double _value;
    private int _timeOut;       // in second
    
    /*
     * Constructors
     */
    
    public Action() {}
    
    public Action(UUID idActuator, double value, int timeOut) {
        _id = UUID.randomUUID();
        _idActuator = idActuator;
        _value = value;
        _timeOut = timeOut;
    }

    public Action(UUID id, UUID idActuator, double value, int timeOut) {
        _id = id;
        _idActuator = idActuator;
        _value = value;
        _timeOut = timeOut;
    }

    /*
     * Getters / Setters
     */

    public UUID getId() {
        return _id;
    }

    public UUID getIdActuator() {
        return _idActuator;
    }

    public void setIdActuator(UUID idActuator) {
        _idActuator = idActuator;
    }

    public int getTimeOut() {
        return _timeOut;
    }

    public void setTimeOut(int timeOut) {
        _timeOut = timeOut;
    }

    public double getValue() {
        return _value;
    }

    public void setValue(double value) {
        _value = value;
    }

    /*
    * Overrides
    */

    @Override
    public String toString() {
        return "\n* * * Action " + getId() + " * * *"
                + "\nActuator: " + getIdActuator()
                + "\nValue: " + getValue()
                + "\nTime out: " + getTimeOut() + "\n";
    }

    /*
     * Methods
     */
}
