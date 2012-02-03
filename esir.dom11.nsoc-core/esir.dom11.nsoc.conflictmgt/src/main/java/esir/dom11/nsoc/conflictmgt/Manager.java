package esir.dom11.nsoc.conflictmgt;

import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.Category;
import esir.dom11.nsoc.model.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;


public class Manager {


    /*
    * Class Attributes
    */
    private static Logger logger = LoggerFactory.getLogger(Manager.class.getName());


    /*
    * Attributes
    */
    private LinkedList<Command> _commandBufferList;         // buffer of the last received command, before process and save
    private LinkedList<Command> _commandWithTimeout;        // buffer of the last received command, before process and save
    private HashMap<UUID, Action> _lastActuatorActionMap;    // list of accepted and send actions, for conflict management
    private HashMap<UUID, Long> _lockActuatorMap;            // list of locks on the actuator, updated all the 60s by default
    private long _delay = 60000;
    private ConflictMgt _conflict;

    /*
     * Constructor
     */
    public Manager(ConflictMgt conflict, long delay){

        _delay = delay;
        _conflict = conflict;

        //Initialisation
        _lastActuatorActionMap = new HashMap<UUID, Action>();
        _commandBufferList = new LinkedList<Command>();
        _commandWithTimeout = new LinkedList<Command>();
        _lockActuatorMap = new HashMap<UUID, Long>();
    }

    public Manager(long delay){

        _delay = delay;

        //Initialisation
        _lastActuatorActionMap = new HashMap<UUID, Action>();
        _commandBufferList = new LinkedList<Command>();
        _commandWithTimeout = new LinkedList<Command>();
        _lockActuatorMap = new HashMap<UUID, Long>();
    }

    /*
    * Getters / Setters
    */

    public LinkedList<Command> get_commandBufferList() {
        return _commandBufferList;
    }

    public void set_commandBufferList(LinkedList<Command> _commandBufferList) {
        this._commandBufferList = _commandBufferList;
    }

    public LinkedList<Command> get_commandWithTimeout() {
        return _commandWithTimeout;
    }

    public void set_commandWithTimeout(LinkedList<Command> _commandWithTimeout) {
        this._commandWithTimeout = _commandWithTimeout;
    }

    public HashMap<UUID, Action> get_lastActuatorActionMap() {
        return _lastActuatorActionMap;
    }

    public void set_lastActuatorActionMap(HashMap<UUID, Action> _lastActuatorActionMap) {
        this._lastActuatorActionMap = _lastActuatorActionMap;
    }

    public HashMap<UUID, Long> get_lockActuatorMap() {
        return _lockActuatorMap;
    }

    public void set_lockActuatorMap(HashMap<UUID, Long> _lockActuatorMap) {
        this._lockActuatorMap = _lockActuatorMap;
    }

    /*
    * Methods
    */

    /**
     * isActuatorFree
     *
     * @param action Action
     * @return "true" if the IdActuator of the action isn't in the _lastActuatorActionMap
     */
    public boolean isActuatorFree(Action action) {
        return !_lastActuatorActionMap.containsKey(action.getIdActuator());
    }

    /**
     * updateLock(), manage the lock option
     */
    public void updateLock() {

        //TODO refactor the "for"

        for (Map.Entry<UUID, Long> actMap : _lockActuatorMap.entrySet()) {

            _lockActuatorMap.put(actMap.getKey(), actMap.getValue() - _delay);
                if (actMap.getValue() <= 0){
                    _lockActuatorMap.remove(actMap.getKey());
                }
        }
    }

    /**
     * updateTimeout(), manage the timeout option. Send the
     */
    public void updateTimeout() {
        for (Command cmd : _commandWithTimeout) {
            LinkedList<Boolean> freedom = new LinkedList<Boolean>();

            //is all the actuators' command now free?
            for (Action action : cmd.getActionList()) {
                freedom.push(isActuatorFree(action));
            }
            //if at least one actuator is not free, update the timeout or remove the command
            if (freedom.contains(false)) {
                int index = _commandWithTimeout.indexOf(cmd);
                if (cmd.getTimeOut() != 0) {
                    cmd.setTimeOut(cmd.getTimeOut() - _delay);
                    _commandWithTimeout.remove(index);
                    _commandWithTimeout.push(cmd);
                } else {
                    _commandWithTimeout.remove(index);
                }
            }

            //TODO send2Actuator in updateTimeout()
        }
    }

    private int getPriority(Command cmd) {
        int priority;
        if (cmd.getCategory() == Category.SECURITY) {
            priority = 0;
        } else if (cmd.getCategory() == Category.USER) {
            priority = 1;
        } else {
            priority = 2;   //AUTO priority by default
        }
        return priority;

        //TODO implement priority management
    }
}
