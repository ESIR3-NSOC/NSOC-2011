package esir.dom11.nsoc.model;

import java.util.UUID;

public class Action {

    /*
     * Attributes
     */

    private UUID _id;           //  dao key
    private UUID _idActuator;
    private double _value;

    /*
     * Constructors
     */
    
    public Action() {
        _id = UUID.randomUUID();
    }
    
    public Action(UUID idActuator, double value) {
        _id = UUID.randomUUID();
        _idActuator = idActuator;
        _value = value;
    }

    public Action(UUID id, UUID idActuator, double value) {
        _id = id;
        _idActuator = idActuator;
        _value = value;
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
                + "\nValue: " + getValue();
    }

    /*
     * Methods
     */
}
