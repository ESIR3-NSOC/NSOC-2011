package esir.dom11.nsoc.model;

import java.util.Date;
import java.util.UUID;

public class Task {
    
    /*
     * Attribute
     */
    
    private UUID _id;
    private String _description;
    private Date _createDate;
    private Date _expireDate;
    private TaskState _taskState;
    private String _script;
    
    /*
     * Constructors
     */
    
    public Task() {
    }
    
    public Task(String description, Date createDate, Date expireDate, String script) {
        _id = UUID.randomUUID();
        _description = description;
        _createDate = createDate;
        _expireDate = expireDate;
        _script = script;
    }
    
    public Task(UUID id, String description, Date createDate, Date expireDate, String script, TaskState taskState) {
        _id = id;
        _description = description;
        _createDate = createDate;
        _expireDate = expireDate;
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

    public Date getCreateDate() {
        return _createDate;
    }

    public void setCreateDate(Date createDate) {
        _createDate = createDate;
    }

    public Date getExpireDate() {
        return _expireDate;
    }

    public void setExpireDate(Date expireDate) {
        _expireDate = expireDate;
    }

    public String getScript() {
        return _script;
    }

    public void setScript(String script) {
        _script = script;
    }
    
    /*
     * Overrides
     */
    
    @Override
    public String toString() {
        return "\n* * * Task " + getId() + " * * *"
                        + "\nDescription: " + getDescription()
                        + "\nCreate Date: " + getCreateDate()
                        + "\nExpire Date: " + getExpireDate()
                        + "\nState: " + getTaskState()
                        + "\nScript:\n" + getScript() + "\n";
    }

    /*
     * Methods
     */
}
