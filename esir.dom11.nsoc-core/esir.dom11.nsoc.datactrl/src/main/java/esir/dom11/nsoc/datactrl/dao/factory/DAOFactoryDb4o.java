package esir.dom11.nsoc.datactrl.dao.factory;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbDb4o;
import esir.dom11.nsoc.datactrl.dao.dao.*;
import esir.dom11.nsoc.datactrl.helper.HelperSetupDb4o;

import java.util.Properties;


public class DAOFactoryDb4o extends DAOFactory {

    /*
     * Constructors
     */

    protected DAOFactoryDb4o(Properties dbProperties) {
        _connectionDb = new ConnectionDbDb4o(dbProperties);
        _connectionDb.connect();
        _helperSetup = new HelperSetupDb4o(this);
    }

    /*
     * Overrides
     */

    @Override
    public ActionDAO getActionDAO() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CommandDAO getCommandDAO() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public DataDAO getDataDAO() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LogDAO getLogDAO() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TaskDAO getTaskDAO() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UserDAO getUserDAO() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
