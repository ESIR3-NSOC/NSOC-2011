package esir.dom11.nsoc.model;

public enum LogLevel {
    TRACE,
    DEBUG,
    INFO,
    WARNING,
    ERROR,
    FATAL;

    public String getValue() {
        return this.name();
    }

}
