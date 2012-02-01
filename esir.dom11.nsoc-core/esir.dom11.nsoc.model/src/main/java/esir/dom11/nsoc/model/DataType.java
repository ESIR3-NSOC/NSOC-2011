package esir.dom11.nsoc.model;

public enum DataType {
    UNKNOWN,
    TEMPERATURE,
    BRIGHTNESS,
    HUMIDITY,
    POWER;

    public String getValue() {
        return this.name();
    }
}
