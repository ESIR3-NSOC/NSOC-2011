package esir.dom11.nsoc.model;

public enum DataType {
    UNKNOWN,
    LIGHT,
    TEMPERATURE,
    BRIGHTNESS,
    HUMIDITY,
    POWER;

    public String getValue() {
        return this.name();
    }
}
