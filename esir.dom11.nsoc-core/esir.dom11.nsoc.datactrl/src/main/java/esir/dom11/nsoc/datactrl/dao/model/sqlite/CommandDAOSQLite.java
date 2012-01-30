package esir.dom11.nsoc.datactrl.dao.model.sqlite;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbSQLite;
import esir.dom11.nsoc.datactrl.dao.dao.CommandDAO;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactorySQLite;
import esir.dom11.nsoc.model.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class CommandDAOSQLite implements CommandDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(CommandDAOSQLite.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbSQLite _connection;
    private DAOFactorySQLite _daoFactory;

    /*
     * Constructors
     */

    public CommandDAOSQLite(ConnectionDbSQLite connectionDbSQLite, DAOFactorySQLite daoFactorySQLite) {
        _connection = connectionDbSQLite;
        _daoFactory = daoFactorySQLite;
    }

    /*
     * Overrides
     */

    @Override
    public Command create(Command command) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Command retrieve(UUID uuid) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Command update(Command command) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
