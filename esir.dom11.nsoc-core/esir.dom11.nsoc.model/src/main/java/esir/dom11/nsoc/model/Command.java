package esir.dom11.nsoc.model;

import java.util.LinkedList;
import java.util.UUID;

public class Command {

    /*
     * Attributes
     */

    private UUID _id;                            // dao key
    private LinkedList<Action> _actionList;
    private Category _category;
    private long _lock;                          // lock time, in ms (min 60s), O if none
    private long _timeOut;                       // time for the command to expire, in ms

    /*
     * Constructors
     */
    
    public Command() {}
    
    public Command(LinkedList<Action> actionList, Category category) {
        _id = UUID.randomUUID();
        _actionList = actionList;
        _category = category;
        _timeOut = 0;
        _lock = 0;

    }


    public Command(UUID id, LinkedList<Action> actionList, Category category, long lock) {
        _id = id;
        _actionList = actionList;
        _category = category;
        _timeOut = 0;
        _lock = lock;

    }

    public Command(UUID id, LinkedList<Action> actionList, Category category, long lock, long timeOut) {
        _id = id;
        _actionList = actionList;
        _category = category;
        _timeOut = timeOut;
        _lock = lock;

    }

    /*
     * Getters / Setters
     */

    public UUID getId() {
        return _id;
    }

    public LinkedList<Action> getActionList() {
        return _actionList;
    }

    public void setActionList(LinkedList<Action> actionList) {
        _actionList = actionList;
    }

    public Category getCategory() {
        return _category;
    }

    public void setCategory(Category category) {
        _category = category;
    }

    public long getTimeOut() {
        return _timeOut;
    }

    public void setTimeOut(long timeOut) {
        _timeOut = timeOut;
    }

    public long getLock() {
        return _lock;
    }

    public void setLock(int lock) {
        _lock = lock;
    }

    /*
    * Overrides
    */

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n* * * Command " + getId() + " * * *"
                                    + "\nCategory: " + getCategory()
                                    + "\nLock time: " + getLock()
                                    + "\nTimeout: " + getTimeOut()

                                    + "\nActions:\n");
        for (Action action : _actionList) {
            stringBuilder.append(action.toString());
        }
        stringBuilder.append("\n* * *     * * *\n");
        return stringBuilder.toString();
    }

    /*
     * Methods
     */
}
