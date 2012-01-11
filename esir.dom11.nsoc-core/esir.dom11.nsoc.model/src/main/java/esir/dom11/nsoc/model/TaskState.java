package esir.dom11.nsoc.model;

public enum TaskState {
    WAITING,
    PROGRESS,
    FINISHED,
    CANCELED,
    FAILED,
    EXPIRED;

    public String getValue() {
        return this.name();
    }
}
