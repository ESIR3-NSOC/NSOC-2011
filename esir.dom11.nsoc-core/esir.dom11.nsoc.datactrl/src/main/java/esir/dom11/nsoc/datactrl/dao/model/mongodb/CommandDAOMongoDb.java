package esir.dom11.nsoc.datactrl.dao.model.mongodb;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMongoDb;
import esir.dom11.nsoc.datactrl.dao.dao.CommandDAO;
import esir.dom11.nsoc.model.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class CommandDAOMongoDb implements CommandDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(CommandDAOMongoDb.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMongoDb _connection;

    /*
     * Constructors
     */

    public CommandDAOMongoDb(ConnectionDbMongoDb connectionDbMongoDb) {
        _connection = connectionDbMongoDb;
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
