package esir.dom11.nsoc.datactrl.component;

import esir.dom11.nsoc.datactrl.process.RequestMgt;
import esir.dom11.nsoc.model.Log;
import esir.dom11.nsoc.service.RequestResult;


// Kevoree imports
import org.kevoree.annotation.*;
import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.Port;
import org.kevoree.framework.*;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactory;
import esir.dom11.nsoc.service.IDbService;

// Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Properties;

@Provides({
        @ProvidedPort(name = "dbService", type = PortType.SERVICE, className = IDbService.class),
        @ProvidedPort(name = "log", type = PortType.MESSAGE)/*,
    @ProvidedPort(name = "broadcast", type = PortType.MESSAGE)*/
})
/*@Requires({
    @RequiredPort(name = "subscribe", type = PortType.MESSAGE)
})*/
@DictionaryType({
        // Db type
        @DictionaryAttribute(name = "dbType", defaultValue = "DAO_MYSQL", optional = true, vals = {"DAO_MYSQL", "DAO_MONGODB"}),
        // Db url
        @DictionaryAttribute(name = "dbUrl", defaultValue = "jdbc:mysql://localhost"),
        // Db port
        @DictionaryAttribute(name = "dbPort", defaultValue = ""),
        // Db user
        @DictionaryAttribute(name = "dbUser", defaultValue = "root"),
        // Db password
        @DictionaryAttribute(name = "dbPwd", defaultValue = ""),
        // Db name
        @DictionaryAttribute(name = "dbName", defaultValue = "nsoc11")
})
@Library(name = "NSOC_2011")
@ComponentType
public class DataCtrl extends AbstractComponentType implements IDbService {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(DataCtrl.class.getName());

    /*
    * Attributes
    */

    private DAOFactory _daoFactory;

    /*
     * Getters / Setters
     */

    /*
    * Overrides
    */

    @Start
    public void start() {
        logger.info("= = = = = start dao = = = = = =");

        Properties dbProperties = new Properties();
        dbProperties.put("url", getDictionary().get("dbUrl"));
        dbProperties.put("port", getDictionary().get("dbPort"));
        dbProperties.put("user", getDictionary().get("dbUser"));
        dbProperties.put("pwd", getDictionary().get("dbPwd"));
        dbProperties.put("name", getDictionary().get("dbName"));
        dbProperties.put("type",  getDictionary().get("dbType"));

        _daoFactory = DAOFactory.getFactory(dbProperties);
        //_daoFactory.getHelperSetup().setupTable();
        //_daoFactory.getHelperSetup().setupData();
        /*User user = _daoFactory.getUserDAO().retrieve("test3_id");
        if (_daoFactory.getUserDAO().delete(user.getId())) {
            logger.warn(".............. delete ................");
        }*/

        //Task task = _daoFactory.getTaskDAO().retrieve(UUID.fromString("e1f4f0a9-2d56-11e1-8e5b-0021cc4198bb"));

        /*LinkedList<Task> taskList = _daoFactory.getTaskDAO().findByState(TaskState.WAITING);
        for ( Task task : taskList) {
            logger.warn(task.toString());
        }*/
    }

    @Stop
    public void stop() {
        logger.info("= = = = = stop dao = = = = = =");
        _daoFactory.getConnectionDb().disconnect();
    }

    @Update
    public void update() {
        logger.info("= = = = = update dao = = = = = =");
    }

    @Override
    @Port(name = "dbService", method = "create")
    public Object create(Object obj) {
        return _daoFactory.getDAO(obj.getClass()).create(obj);
    }

    @Override
    @Port(name = "dbService", method = "retrieve")
    public Object retrieve(String className, Object id) {
        try {
            return _daoFactory.getDAO(Class.forName(className)).retrieve(id);
        } catch (ClassNotFoundException exception) {
            logger.error("Retrieve User Error: Class " + className + "does not exist.",exception);
            return null;
        }
    }

    @Override
    @Port(name = "dbService", method = "update")
    public Object update(Object obj) {
        return _daoFactory.getDAO(obj.getClass()).update(obj);
    }

    @Override
    @Port(name = "dbService", method = "delete")
    public boolean delete(String className, Object id) {
        try {
            return _daoFactory.getDAO(Class.forName(className)).delete(id);
        } catch (ClassNotFoundException exception) {
            logger.error("Delete User Error: Class " + className + "does not exist.",exception);
            return false;
        }
    }

    @Override
    @Port(name = "dbService", method = "get")
    public RequestResult get(String methodName, String className, LinkedList<Object> params) {
        Object[] p = params.toArray();
        RequestMgt requestMgt = new RequestMgt(_daoFactory,methodName,className,p);
        return requestMgt.getResult();
    }

    @Ports({
        @Port(name = "log")
    })
    public void log(Object log) {
        _daoFactory.getLogDAO().create((Log)log);
    }

    /*
    * Methods
    */

    /*public void broadcast(HashMap<String, String> params) {
        logger.debug("Broadcast :" + params);
        getPortByName("broadcast", MessagePort.class).process(params);
    } */
}
