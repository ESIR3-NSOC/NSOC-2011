package esir.dom11.nsoc.conflictmgt;

import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.Category;
import esir.dom11.nsoc.model.Command;
import esir.dom11.nsoc.model.Log;
import esir.dom11.nsoc.model.LogLevel;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Provides({
        @ProvidedPort(name = "cmdFromCtrl", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "actToActuator", type = PortType.MESSAGE)
})
@Library(name = "NSOC_2011")
@ComponentType
public class ConflictMgt extends AbstractComponentType {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ConflictMgt.class.getName());

    /*
    * Attributes
    */
    private long updateDelay = 2000;//60000;                       //time between updates of locks, in ms (60s by befault)

    private LinkedList<Command> _commandBufferList;         // buffer of the last received command, before process and save
    private LinkedList<Command> _commandWithTimeout;        // buffer of the last received command, before process and save
    private HashMap<UUID,Action> _lastActuatorActionMap;    // list of accepted and send actions, for conflict management
    private HashMap<UUID,Long> _lockActuatorMap;            // list of locks on the actuator, updated all the 60s by default

    /*
    * Getters / Setters
    */

    /*
    * Overrides
    */

    /**
     *
     */
    @Start
    public void start() {
        logger.info("= = = = = start conflict manager = = = = = =");

        //Initialisation
        _lastActuatorActionMap = new HashMap<UUID, Action>();
        _commandBufferList = new LinkedList<Command>();
        _commandWithTimeout = new LinkedList<Command>();
        _lockActuatorMap = new HashMap<UUID, Long>();

        //Timer initialisation
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateLock();
                sendLog("Lock times are updated", LogLevel.INFO);
                updateTimeout();
                sendLog("Timeouts and command in buffer are updated", LogLevel.INFO);
            }
        }, updateDelay);
    }

    @Stop
    public void stop() {
        logger.info("= = = = = stop conflict manager = = = = = =");
    }

    @Update
    public void update() {
        logger.info("= = = = = update conflict manager = = = = = =");
    }

    /*
    * Overrides
    */

    /**
     * cmdFromCtrl
     *
     * @param command Command
     */
    @Port(name = "cmdFromCtrl")
    public void cmdFromCtrl(Object command) {

        boolean commandToSave = false;

        Command cmd = (Command)command;

        // Save of the command to process
        _commandBufferList.add(cmd);

        // Retrieve lock times and send authored actions
        for (Action action : cmd.getActionList()) {

            _lockActuatorMap.put(action.getIdActuator(), cmd.getLock());

            if (isActuatorFree(action)) {
                send2Actuator(action);
            }

            // if one action can't be done, don't check the others of the command
            else {
                commandToSave = true;
                break;}
        }

        // if the command can't be done and if the timeout isn't zero, save the command in _commandWithTimeout
        if (commandToSave && cmd.getTimeOut()!=0){
            _commandWithTimeout.add(cmd);
        }
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
    private boolean isActuatorFree(Action action) {
        return !_lastActuatorActionMap.containsKey(action.getIdActuator());
    }

    /**
     *  updateLock(), manage the lock option
     */
    private void updateLock(){
        //
        for(Map.Entry<UUID,Long> actMap: _lockActuatorMap.entrySet()){
            if (actMap.getValue()!=0){
                _lockActuatorMap.put(actMap.getKey(),actMap.getValue()- updateDelay);
            }
            else {
                _lockActuatorMap.remove(actMap.getKey());}
        }
    }

    /**
     *  updateTimeout(), manage the timeout option. Send the
     */
    private void updateTimeout(){
        for(Command cmd: _commandWithTimeout){
            LinkedList<Boolean> freedom = new LinkedList<Boolean>();

            //is all the actuators' command now free?
            for (Action action : cmd.getActionList()){
                freedom.push(isActuatorFree(action));
            }
            //if at least one actuator is not free, update the timeout or remove the command
            if (freedom.contains(false)){
                int index = _commandWithTimeout.indexOf(cmd);
                if (cmd.getTimeOut()!=0){
                    cmd.setTimeOut(cmd.getTimeOut()- updateDelay);
                    _commandWithTimeout.remove(index);
                    _commandWithTimeout.push(cmd);
                }
                else {
                    _commandWithTimeout.remove(index);
                }
            }

            //TODO send2Actuator in updateTimeout()
        }
    }

    /**
     * send2Actuator, save the last action for the lock option and send it to the actuator
     *
     * @param action Action
     */

    private void send2Actuator(Action action){
        _lastActuatorActionMap.put(action.getIdActuator(),action);
        getPortByName("actToActuator",MessagePort.class).process(action);
    }


    /**
     *
     * @param str
     * @param lvl
     */
    private void sendLog(String str,LogLevel lvl){
        Log log = new Log(ConflictMgt.class.getName(), str, lvl);
        getPortByName("actToActuator",MessagePort.class).process(log);
    }
    
    private void saveInDb() {
        // TODO saveInDb
    }

    private int getPriority(Command cmd) {
        int priority;
        if (cmd.getCategory()==Category.SECURITY){
            priority=0;
        }
        else if (cmd.getCategory()==Category.USER){
            priority=1;
        }
        else {
            priority=2;   //AUTO priority by default
        }
        return priority;

        //TODO implement priority management
    }
}
