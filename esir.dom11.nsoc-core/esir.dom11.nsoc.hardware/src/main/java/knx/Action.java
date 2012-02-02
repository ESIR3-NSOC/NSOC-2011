package knx;

import java.util.UUID;

public class Action {

    /*
     * Attributes
     */

    private UUID _id;           //  dao key
    private String _idActuator;
    private Object _value;

    /*
     * Constructors
     */
    
    public Action() {}
    
    public Action(String idActuator, Object value) {
        _id = UUID.randomUUID();
        _idActuator = idActuator;
        _value = value;
    }

    public Action(UUID id, String idActuator, double value) {
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

    public String getIdActuator() {
        return _idActuator;
    }

    public void setIdActuator(String idActuator) {
        _idActuator = idActuator;
    }

    public Object getValue() {
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
