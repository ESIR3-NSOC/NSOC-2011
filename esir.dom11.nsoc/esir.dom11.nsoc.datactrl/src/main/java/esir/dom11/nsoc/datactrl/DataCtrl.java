package esir.dom11.nsoc.datactrl;

// Kevoree imports
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactory;
import esir.dom11.nsoc.datactrl.dao.factory.FactoryType;
import esir.dom11.nsoc.model.User;
import esir.dom11.nsoc.service.IDbService;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

// Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@Provides({
    @ProvidedPort(name = "dbService", type = PortType.SERVICE, className = IDbService.class)/*,
    @ProvidedPort(name = "broadcast", type = PortType.MESSAGE)*/
})
/*@Requires({
    @RequiredPort(name = "subscribe", type = PortType.MESSAGE)
})*/
@DictionaryType({
        // Db url
        @DictionaryAttribute(name = "dbUrl", defaultValue = "jdbc:mysql://localhost"),
        // Db user
        @DictionaryAttribute(name = "dbUser", defaultValue = "root"),
        // Db password
        @DictionaryAttribute(name = "dbPwd", defaultValue = ""),
        // Db name
        @DictionaryAttribute(name = "dbName", defaultValue = "nsoc11"),
        // Db type
        @DictionaryAttribute(name = "dbType", defaultValue = "DAO_MYSQL")
})
@Library(name = "DataCtrl")
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
        dbProperties.put("user", getDictionary().get("dbUser"));
        dbProperties.put("pwd", getDictionary().get("dbPwd"));
        dbProperties.put("name", getDictionary().get("dbName"));
        dbProperties.put("type",  getDictionary().get("dbType"));

        _daoFactory = DAOFactory.getFactory(dbProperties);
        /*User user = _daoFactory.getUserDAO().retrieve("test3_id");
        if (_daoFactory.getUserDAO().delete(user.getId())) {
            logger.warn(".............. delete ................");
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

    /*@Port(name = "subscribe")
    public void subscribe(Object object) {

    }*/

    /*
    * Methods
    */

    /*public void broadcast(HashMap<String, String> params) {
        logger.debug("Broadcast :" + params);
        getPortByName("brodcast", MessagePort.class).process(params);
    } */
}
