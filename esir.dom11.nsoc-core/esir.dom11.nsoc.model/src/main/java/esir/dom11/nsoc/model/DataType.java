package esir.dom11.nsoc.model;

public enum DataType {
    UNKNOWN,
    LIGHT,
    TEMPERATURE,
    BRIGHTNESS,
    HUMIDITY,
    POWER,
    PRESENCE,
    SWITCH; //trigger a event

    public String getValue() {
        return this.name();
    }
}
