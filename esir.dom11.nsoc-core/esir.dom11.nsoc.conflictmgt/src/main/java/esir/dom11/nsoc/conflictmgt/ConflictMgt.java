package esir.dom11.nsoc.conflictmgt;

/*
 TODO: thread gestion temps lock, save list cmd et action dans bd (30min), gérer condition actuator free, gérer catégorie
  */

import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.Command;
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
    private long UpdateDelay = 60000;                   //time between updates of locks, in ms (60s by befault)
    private static int SECURITY = 0;
    private static int USER = 1;
    private static int AUTO = 2;

    private LinkedList<Command> _commandBufferList;         // buffer of the last received command, before process and save
    private LinkedList<Command> _commandWithTimeout;        // buffer of the last received command, before process and save
    private HashMap<UUID,Action> _lastActuatorActionMap;    // list of accepted and send actions, for conflict management
    private HashMap<UUID,Long> _lockActuatorMap;             // list of locks on the actuator, updated all the 60s by default
    private Timer timer;

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
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                // Manage the timeout options, and update it
                for(Command cmd: _commandWithTimeout){
                    boolean[] freedom = new boolean[cmd.getActionList().size()];

                    //is all the actuators command now free?
                    for (Action action : cmd.getActionList()){
                        if(isActuatorFree(action)){

                        }
                    }

                    //update the timeout if not free
                    int index = _commandWithTimeout.indexOf(cmd);
                    if (cmd.getTimeOut()!=0){
                        cmd.setTimeOut(cmd.getTimeOut()- UpdateDelay);
                        _commandWithTimeout.remove(index);
                        _commandWithTimeout.push(cmd);
                    }
                    else {
                        _commandWithTimeout.remove(index);
                    }
                }

                // Manage the lock option 
                for(Map.Entry<UUID,Long> actMap: _lockActuatorMap.entrySet()){
                    if (actMap.getValue()!=0){
                        _lockActuatorMap.put(actMap.getKey(),actMap.getValue()- UpdateDelay);
                    }
                    else {
                        _lockActuatorMap.remove(actMap.getKey());}
                }
            }
        }, UpdateDelay);
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
     * @param command
     */
    @Port(name = "cmdFromCtrl")
    public void cmdFromCtrl(Command command) {

        boolean commandToSave = false;

        // Save of the command to process
        _commandBufferList.add(command);

        // Retrieve lock times and send authored actions
        for (Action action : command.getActionList()) {

            _lockActuatorMap.put(action.getIdActuator(), command.getLock());

            if (isActuatorFree(action)) {
                send2Actuator(action);
            }

            // if one action can't be done, don't check the others of the command
            else {
                commandToSave = true;
                break;}
        }

        // if the command can't be done and if the timeout isn't zero, save the command in _commandWithTimeout
        if (commandToSave && command.getTimeOut()!=0){
            _commandWithTimeout.add(command);
        }

    }

    /*
     * Methods
     */

    private void send2Actuator(Action action){
        _lastActuatorActionMap.put(action.getIdActuator(),action);
        getPortByName("actToActuator",MessagePort.class).process(action);
    }

    /**
     * isActuatorFree
     * @param action
     * @return "true" if the IdActuator of the action isn't in the _lastActuatorActionMap
     */
    private boolean isActuatorFree(Action action) {
        if (_lastActuatorActionMap.containsKey(action.getIdActuator())){
            return false;
        }
        else {
            return true;
        }
    }

    private void saveInDb() {
        // TODO
    }

    private int getPriority() {
        // TODO
        return 0;
    }
}