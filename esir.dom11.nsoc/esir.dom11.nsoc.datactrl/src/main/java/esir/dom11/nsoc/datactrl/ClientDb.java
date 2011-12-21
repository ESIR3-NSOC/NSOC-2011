package esir.dom11.nsoc.datactrl;

// Logger
import esir.dom11.nsoc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import esir.dom11.nsoc.service.IDbService;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

@Requires({
        @RequiredPort(name = "dbService", type = PortType.SERVICE, className = IDbService.class, needCheckDependency = true)
})
@Library(name = "DataCtrl")
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
        /*logger.info("**** clientdb start ****");
        User userRetrieve = (User)getPortByName("dbService", IDbService.class).retrieve(User.class.getName(),"test_externe_id");
        logger.warn("**** User retrieved: "+ userRetrieve +" ****");
        userRetrieve.setPwd("new_pwd");
        User userUpdate = (User)getPortByName("dbService", IDbService.class).update(userRetrieve);
        logger.warn("**** User saved: "+ userUpdate +" ****");
        getPortByName("dbService", IDbService.class).delete(User.class.getName(),"test_externe_id");*/
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
