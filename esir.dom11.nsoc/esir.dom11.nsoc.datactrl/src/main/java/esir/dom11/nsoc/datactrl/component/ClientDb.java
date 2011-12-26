package esir.dom11.nsoc.datactrl.component;

// Logger
import esir.dom11.nsoc.model.Task;
import esir.dom11.nsoc.model.TaskState;
import esir.dom11.nsoc.service.RequestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import esir.dom11.nsoc.service.IDbService;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

import java.util.LinkedList;

@Requires({
        @RequiredPort(name = "dbService", type = PortType.SERVICE, className = IDbService.class, needCheckDependency = true)
})
@Library(name = "NSOC_2011")
@ComponentType
public class ClientDb extends AbstractComponentType {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ClientDb.class.getName());

    /*
     * Overrides
     */

    @Start
    public void start() {
        logger.info("**** clientdb start ****");
        /*User userRetrieve = (User)getPortByName("dbService", IDbService.class).retrieve(User.class.getName(),"test_externe_id");
        logger.warn("**** User retrieved: "+ userRetrieve +" ****");
        userRetrieve.setPwd("new_pwd");
        User userUpdate = (User)getPortByName("dbService", IDbService.class).update(userRetrieve);
        logger.warn("**** User saved: "+ userUpdate +" ****");
        getPortByName("dbService", IDbService.class).delete(User.class.getName(),"test_externe_id");*/

        LinkedList<Object> params = new LinkedList<Object>();
        params.add(TaskState.WAITING);
        RequestResult result = getPortByName("dbService", IDbService.class).get("findByState", Task.class.getName(), params);
        if (result.isSuccess()) {
            for ( Task task : (LinkedList<Task>)result.getResult()) {
                logger.warn(task.toString());
            }
        }
    }

    @Stop
    public void stop() {
        logger.warn("**** clientdb stop ****");
    }

    @Update
    public void update() {
        logger.warn("**** clientdb update ****");
    }
}
