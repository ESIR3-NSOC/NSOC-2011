package esir.dom11.nsoc.datactrl.dao.model.sqlite;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbSQLite;
import esir.dom11.nsoc.datactrl.dao.dao.ActionDAO;
import esir.dom11.nsoc.model.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ActionDAOSQLite implements ActionDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ActionDAOSQLite.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbSQLite _connection;

    /*
     * Constructors
     */

    public ActionDAOSQLite(ConnectionDbSQLite connectionDbSQLite) {
        _connection = connectionDbSQLite;
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
