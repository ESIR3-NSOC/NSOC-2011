package esir.dom11.nsoc.scheduler;

import esir.dom11.nsoc.model.Task;
import esir.dom11.nsoc.service.IDbService;
import esir.dom11.nsoc.service.ISchedulerService;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

@Provides({
    @ProvidedPort(name = "schedulerService", type = PortType.SERVICE, className = ISchedulerService.class)
})
@Requires({
    @RequiredPort(name = "dbService", type = PortType.SERVICE, className = IDbService.class, needCheckDependency = true)
})
@Library(name = "NSOC_2011")
@ComponentType
public class Scheduler extends AbstractComponentType implements ISchedulerService {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(Scheduler.class.getName());

    /*
    * Attributes
    */

    private LinkedList<Task> _taskList;

    /*
     * Getters / Setters
     */

    /*
    * Overrides
    */

    @Start
    public void start() {
        logger.info("= = = = = start scheduler = = = = = =");
    }

    @Stop
    public void stop() {
        logger.info("= = = = = stop scheduler = = = = = =");
    }

    @Update
    public void update() {
        logger.info("= = = = = update scheduler = = = = = =");
    }

    @Override
    @Port(name = "schedulerService", method = "scheduleTask")
    public void scheduleTask(Task task) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Port(name = "schedulerService", method = "cancelTask")
    public void cancelTask(Task task) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
