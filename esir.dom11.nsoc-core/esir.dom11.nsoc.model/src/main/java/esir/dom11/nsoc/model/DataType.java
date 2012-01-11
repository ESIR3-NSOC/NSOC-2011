package esir.dom11.nsoc.model;

public enum DataType {
    TEMPERATURE,
    BRIGHTNESS,
    HUMIDITY,
    POWER;

    public String getValue() {
        return this.name();
    }
}
