package esir.dom11.nsoc.datactrl.dao.factory;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.*;
import esir.dom11.nsoc.datactrl.dao.model.mysql.*;
import esir.dom11.nsoc.datactrl.helper.HelperSetupMySQL;

import java.util.Properties;

public class DAOFactoryMySQL extends DAOFactory {

    protected DAOFactoryMySQL(Properties dbProperties) {
        _connectionDb = new ConnectionDbMySQL(dbProperties);
        _connectionDb.connect();
        _helperSetup = new HelperSetupMySQL(this);
    }

    @Override
    public ActionDAO getActionDAO() {
        return new ActionDAOMySQL((ConnectionDbMySQL)_connectionDb);
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return new CategoryDAOMySQL((ConnectionDbMySQL)_connectionDb);
    }

    @Override
    public CommandDAO getCommandDAO() {
        return new CommandDAOMySQL((ConnectionDbMySQL)_connectionDb, this);
    }

    @Override
    public DataDAO getDataDAO() {
        return new DataDAOMySQL((ConnectionDbMySQL)_connectionDb);
    }

    @Override
    public TaskDAO getTaskDAO() {
        return new TaskDAOMySQL((ConnectionDbMySQL)_connectionDb);
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOMySQL((ConnectionDbMySQL)_connectionDb);
    }
}
