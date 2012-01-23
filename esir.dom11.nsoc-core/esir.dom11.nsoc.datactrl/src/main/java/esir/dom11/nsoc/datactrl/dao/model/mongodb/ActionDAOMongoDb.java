package esir.dom11.nsoc.datactrl.dao.model.mongodb;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMongoDb;
import esir.dom11.nsoc.datactrl.dao.dao.ActionDAO;
import esir.dom11.nsoc.model.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ActionDAOMongoDb implements ActionDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ActionDAOMongoDb.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMongoDb _connection;

    /*
     * Constructors
     */

    public ActionDAOMongoDb(ConnectionDbMongoDb connectionDbMongoDb) {
        _connection = connectionDbMongoDb;
    }

    /*
     * Overrides
     */

    @Override
    public Action create(Action action) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Action retrieve(UUID uuid) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Action update(Action action) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
