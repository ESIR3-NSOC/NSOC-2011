package esir.dom11.nsoc.datactrl.dao.factory;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMongoDb;
import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.*;
import esir.dom11.nsoc.datactrl.dao.model.mongodb.*;
import esir.dom11.nsoc.datactrl.dao.model.mysql.ActionDAOMySQL;
import esir.dom11.nsoc.datactrl.helper.HelperSetupMongoDb;

import java.util.Properties;

public class DAOFactoryMongoDb extends DAOFactory {

    /*
     * Constructors
     */

    protected DAOFactoryMongoDb(Properties dbProperties) {
        _connectionDb = new ConnectionDbMongoDb(dbProperties);
        _connectionDb.connect();
        _helperSetup = new HelperSetupMongoDb(this);
    }

    /*
     * Overrides
     */

    @Override
    public ActionDAO getActionDAO() {
        return new ActionDAOMongoDb((ConnectionDbMongoDb)_connectionDb);
    }

    @Override
    public CommandDAO getCommandDAO() {
        return new CommandDAOMongoDb((ConnectionDbMongoDb)_connectionDb);
    }

    @Override
    public DataDAO getDataDAO() {
        return new DataDAOMongoDb((ConnectionDbMongoDb)_connectionDb, this);
    }

    @Override
    public DeviceDAO getDeviceDAO() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LogDAO getLogDAO() {
        return new LogDAOMongoDb((ConnectionDbMongoDb)_connectionDb);
    }

    @Override
    public TaskDAO getTaskDAO() {
        return new TaskDAOMongoDb((ConnectionDbMongoDb)_connectionDb);
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOMongoDb((ConnectionDbMongoDb)_connectionDb);
    }
}
