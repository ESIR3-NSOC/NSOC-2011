package esir.dom11.nsoc.datactrl.dao.model.db4o;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbDb4o;
import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.ActionDAO;
import esir.dom11.nsoc.model.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ActionDAODb4o implements ActionDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ActionDAODb4o.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbDb4o _connection;

    /*
     * Constructors
     */

    public ActionDAODb4o(ConnectionDbDb4o connectionDbDb4o) {
        _connection = connectionDbDb4o;
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
