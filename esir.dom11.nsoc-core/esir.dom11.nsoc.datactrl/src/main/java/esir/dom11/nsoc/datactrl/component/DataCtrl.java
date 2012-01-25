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
        @DictionaryAttribute(name = "dbName", defaultValue = "nsoc11"),
        // Db setup
        @DictionaryAttribute(name = "Setup", defaultValue = "CREATE & INSERT FIRST", optional = true, vals = {"CREATE & INSERT", "CREATE & INSERT FIRST", "DO NOTHING"}),
        // Db transfer
        @DictionaryAttribute(name = "Transfer", defaultValue = "NO", optional = true, vals = {"NO", "YES"})
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
    private Properties _dbProperties;

    /*
     * Getters / Setters
     */

    /*
    * Overrides
    */

    @Start
    public void start() {
        logger.info("= = = = = start data controller = = = = = =");

        _dbProperties = new Properties();
        _dbProperties.put("url", getDictionary().get("dbUrl"));
        _dbProperties.put("port", getDictionary().get("dbPort"));
        _dbProperties.put("user", getDictionary().get("dbUser"));
        _dbProperties.put("pwd", getDictionary().get("dbPwd"));
        _dbProperties.put("name", getDictionary().get("dbName"));
        _dbProperties.put("type",  getDictionary().get("dbType"));

        _daoFactory = DAOFactory.getFactory(_dbProperties);

        if (((String)getDictionary().get("Setup")).compareTo("CREATE & INSERT")==0
                || ((String)getDictionary().get("Setup")).compareTo("CREATE & INSERT FIRST")==0) {
            _daoFactory.getHelperSetup().setupTable();
            _daoFactory.getHelperSetup().setupData();
        }
    }

    @Stop
    public void stop() {
        logger.info("= = = = = stop data controller = = = = = =");
        _daoFactory.getConnectionDb().disconnect();
    }

    @Update
    public void update() {
        logger.info("= = = = = update data controller = = = = = =");

        Properties dbProperties = new Properties();
        dbProperties.put("url", getDictionary().get("dbUrl"));
        dbProperties.put("port", getDictionary().get("dbPort"));
        dbProperties.put("user", getDictionary().get("dbUser"));
        dbProperties.put("pwd", getDictionary().get("dbPwd"));
        dbProperties.put("name", getDictionary().get("dbName"));
        dbProperties.put("type",  getDictionary().get("dbType"));

        // check changes

        // db type, url, name or port change: new connection
        if (_dbProperties.getProperty("type").compareTo((String)getDictionary().get("dbType"))!=0
                || _dbProperties.getProperty("url").compareTo((String)getDictionary().get("dbUrl"))!=0
                || _dbProperties.getProperty("name").compareTo((String)getDictionary().get("dbName"))!=0
                || _dbProperties.getProperty("port").compareTo((String)getDictionary().get("dbPort"))!=0) {

            DAOFactory daoFactory = DAOFactory.getFactory(dbProperties);

            // if transfer request
            if (((String)getDictionary().get("Transfer")).compareTo("YES")==0) {
                daoFactory.getHelperSetup().importDb(_daoFactory.getHelperSetup().exportDb());
            } else if (((String)getDictionary().get("Setup")).compareTo("CREATE & INSERT")==0) {
                daoFactory.getHelperSetup().setupTable();
                daoFactory.getHelperSetup().setupData();
            }
            _daoFactory.getConnectionDb().disconnect();
            _daoFactory = daoFactory;
        } else if (_dbProperties.getProperty("user").compareTo((String)getDictionary().get("dbUser"))!=0
                        || _dbProperties.getProperty("pwd").compareTo((String)getDictionary().get("dbPwd"))!=0) {
            _daoFactory.getConnectionDb().disconnect();
            _daoFactory = DAOFactory.getFactory(dbProperties);
        }
        _dbProperties = dbProperties;
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

    @Port(name = "log")
    public void log(Object log) {
        logger.info("Saving log...");
        Log savedLog = _daoFactory.getLogDAO().create((Log)log);
        logger.info(savedLog.toString());
    }

    /*
    * Methods
    */

    /*public void broadcast(HashMap<String, String> params) {
        logger.debug("Broadcast :" + params);
        getPortByName("broadcast", MessagePort.class).process(params);
    } */
}
