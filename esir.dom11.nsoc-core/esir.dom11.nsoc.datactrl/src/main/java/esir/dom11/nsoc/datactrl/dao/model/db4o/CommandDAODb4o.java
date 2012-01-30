package esir.dom11.nsoc.datactrl.dao.model.db4o;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbDb4o;
import esir.dom11.nsoc.datactrl.dao.dao.CommandDAO;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryDb4o;
import esir.dom11.nsoc.model.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class CommandDAODb4o implements CommandDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(CommandDAODb4o.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbDb4o _connection;
    private DAOFactoryDb4o _daoFactory;

    /*
     * Constructors
     */

    public CommandDAODb4o(ConnectionDbDb4o connectionDbDb4o, DAOFactoryDb4o daoFactoryDb4o) {
        _connection = connectionDbDb4o;
        _daoFactory = daoFactoryDb4o;
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
