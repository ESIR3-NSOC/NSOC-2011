package esir.dom11.nsoc.datactrl.dao.model.db4o;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbDb4o;
import esir.dom11.nsoc.datactrl.dao.dao.LogDAO;
import esir.dom11.nsoc.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class LogDAODb4o implements LogDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(LogDAODb4o.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbDb4o _connection;

    /*
    * Constructors
    */

    public LogDAODb4o(ConnectionDbDb4o connectionDbDb4o) {
        _connection = connectionDbDb4o;
    }

    /*
     * Overrides
     */

    @Override
    public Log create(Log log) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Log retrieve(UUID uuid) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Log update(Log log) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
