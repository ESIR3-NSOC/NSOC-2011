package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.CommandDAO;
import esir.dom11.nsoc.model.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class CommandDAOMySQL implements CommandDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(CommandDAOMySQL.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMySQL _connection;

    /*
     * Constructors
     */

    public CommandDAOMySQL(ConnectionDbMySQL connectionDbMySQL) {
        _connection = connectionDbMySQL;
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
