package esir.dom11.nsoc.model;

import java.util.Date;
import java.util.UUID;

public class Task {
    
    /*
     * Attribute
     */
    
    private UUID _id;
    private String _description;
    private Date _date;
    private TaskState _taskState;
    private String _script;
    
    /*
     * Constructors
     */
    
    public Task() {
    }
    
    public Task(String description, Date date, String script) {
        _id = UUID.randomUUID();
        _description = description;
        _date = date;
        _script = script;
    }
    
    public Task(UUID id, String description, Date date, String script, TaskState taskState) {
        _id = id;
        _description = description;
        _date = date;
        _taskState = taskState;
        _script = script;
    }

    /*
     * Getters / Setters
     */

    public UUID getId() {
        return _id;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public TaskState getTaskState() {
        return _taskState;
    }

    public void setTaskState(TaskState taskState) {
        this._taskState = taskState;
    }

    public Date getDate() {
        return _date;
    }

    public void setDate(Date date) {
        this._date = date;
    }

    public String getScript() {
        return _script;
    }

    public void setScript(String script) {
        this._script = script;
    }
    
    /*
     * Overrides
     */
    
    @Override
    public String toString() {
        return "\n* * * Task " + getId() + " * * *"
                        + "\nDescription: " + getDescription()
                        + "\nDate: " + getDate()
                        + "\nState: " + getTaskState()
                        + "\nScript:\n" + getScript() + "\n";
    }
}
