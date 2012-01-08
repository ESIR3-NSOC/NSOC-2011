package esir.dom11.nsoc.model;

import java.util.LinkedList;
import java.util.UUID;

public class Command {

    /*
     * Attributes
     */

    private UUID _id;                           // dao key
    private LinkedList<Action> _actionList;
    private Category _category;
    
    /*
     * Constructors
     */
    
    public Command() {}
    
    public Command(LinkedList<Action> actionList, Category category) {
        _id = UUID.randomUUID();
        _actionList = actionList;
        _category = category;
    }

    public Command(UUID id, LinkedList<Action> actionList, Category category) {
        _id = id;
        _actionList = actionList;
        _category = category;
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

    /*
    * Overrides
    */

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n* * * Command " + getId() + " * * *"
                                    + "\nCategory: " + getCategory()
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
