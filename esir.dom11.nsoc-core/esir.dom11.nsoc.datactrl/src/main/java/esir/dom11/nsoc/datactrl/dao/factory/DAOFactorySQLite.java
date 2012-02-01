package esir.dom11.nsoc.datactrl.dao.factory;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbSQLite;
import esir.dom11.nsoc.datactrl.dao.dao.*;
import esir.dom11.nsoc.datactrl.dao.model.sqlite.*;
import esir.dom11.nsoc.datactrl.helper.HelperSetupSQLite;

import java.util.Properties;

public class DAOFactorySQLite extends DAOFactory {

    protected DAOFactorySQLite(Properties dbProperties) {
        _connectionDb = new ConnectionDbSQLite(dbProperties);
        _connectionDb.connect();
        _helperSetup = new HelperSetupSQLite(this);
    }

    @Override
    public ActionDAO getActionDAO() {
        return new ActionDAOSQLite((ConnectionDbSQLite)_connectionDb);
    }

    @Override
    public CommandDAO getCommandDAO() {
        return new CommandDAOSQLite((ConnectionDbSQLite)_connectionDb,this);
    }

    @Override
    public DataDAO getDataDAO() {
        return new DataDAOSQLite((ConnectionDbSQLite)_connectionDb, this);
    }

    @Override
    public DeviceDAO getDeviceDAO() {
        return new DeviceDAOSQLite((ConnectionDbSQLite)_connectionDb);
    }

    @Override
    public LogDAO getLogDAO() {
        return new LogDAOSQLite((ConnectionDbSQLite)_connectionDb);
    }

    @Override
    public TaskDAO getTaskDAO() {
        return new TaskDAOSQLite((ConnectionDbSQLite)_connectionDb);
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOSQLite((ConnectionDbSQLite)_connectionDb);
    }
}
