package esir.dom11.nsoc.conflictmgt;

//import esir.dom11.nsoc.conflictmgt.Manager;

import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.Category;
import esir.dom11.nsoc.model.Command;
import esir.dom11.nsoc.model.Log;
import esir.dom11.nsoc.model.LogLevel;
import esir.dom11.nsoc.service.RequestResult;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.lang.Thread.sleep;

@Provides({
        @ProvidedPort(name = "cmdFromCtrl", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "actToActuator", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "log", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "respToCtrl", type = PortType.MESSAGE, optional = true)
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
    private long updateDelay = 5000;//60000;      //time between updates of locks, in ms (60s by befault)
    private ExecTimer t3;
    private Manager mng;

    /*
    * Getters / Setters
    */

    public long getUpdateDelay() {
        return updateDelay;
    }

    /*
    * Overrides
    */

    /**
     *
     */
    @Start
    public void start() throws InterruptedException {
        logger.info("= = = = = start conflict manager = = = = = =");
        sendLog("Conflict manager is started", LogLevel.INFO);

        //Manager initialisation
        mng = new Manager(this,updateDelay);

        //Timer initialisation
        t3 = new ExecTimer(1,mng,updateDelay);
    }

    @Stop
    public void stop() {
        logger.info("= = = = = stop conflict manager = = = = = =");
        t3.shutdown();
    }

    @Update
    public void update() {
        logger.info("= = = = = update conflict manager = = = = = =");
        t3.shutdown();
        t3 = new ExecTimer(1,mng,updateDelay);
    }

    /**
     * cmdFromCtrl
     *
     * @param command Command
     */
    @Port(name = "cmdFromCtrl")
    public void cmdFromCtrl(Object command) {

        boolean commandToSave = false;
        Command cmd = (Command) command;

        // Save of the command to process
        LinkedList<Command> tmp = mng.get_commandBufferList();
        tmp.add(cmd);
        mng.set_commandBufferList(tmp);

        // Retrieve lock times and send authored actions
        for (Action action : cmd.getActionList()) {

            HashMap<UUID,Long> a = mng.get_lockActuatorMap();
            a.put(action.getIdActuator(), cmd.getLock());
            mng.set_lockActuatorMap(a);

            if (mng.isActuatorFree(action)) {
                send2Actuator(action);
            }

            // if one action can't be done, don't check the others of the command
            else {
                commandToSave = true;
                break;
            }
        }

        // if the command can't be done and if the timeout isn't zero, save the command in _commandWithTimeout
        if (commandToSave && cmd.getTimeOut() != 0) {
            LinkedList<Command> cwt = mng.get_commandWithTimeout();
            cwt.add(cmd);
            mng.set_commandWithTimeout(cwt);
        }
    }

    /*
     * Methods
     */

    /**
     * send2Actuator, save the last action for the lock option and send it to the actuator
     *
     * @param action Action
     */
    public void send2Actuator(Action action) {
        HashMap<UUID,Action> am = mng.get_lastActuatorActionMap();
        am.put(action.getIdActuator(), action);
        mng.set_lastActuatorActionMap(am);
        getPortByName("actToActuator", MessagePort.class).process(action);
    }

    /**
     * @param str
     * @param lvl
     */
    public void sendLog(String str, LogLevel lvl) {
        Log log = new Log(ConflictMgt.class.getName(), str, lvl);
        getPortByName("log", MessagePort.class).process(log);
    }

    /**
     * @param id
     * @param resp
     */
    public void resp2Ctrl(UUID id, boolean resp) {
        getPortByName("respToCtrl", MessagePort.class).process(new RequestResult(id, resp));
    }

    private void saveInDb() {
        // TODO saveInDb
    }
}
