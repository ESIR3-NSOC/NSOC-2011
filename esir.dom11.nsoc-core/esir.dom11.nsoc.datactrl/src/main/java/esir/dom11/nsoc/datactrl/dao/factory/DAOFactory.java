package esir.dom11.nsoc.datactrl.dao.factory;

import esir.dom11.nsoc.datactrl.dao.dao.*;
import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDb;
import esir.dom11.nsoc.datactrl.helper.HelperSetup;
import esir.dom11.nsoc.model.*;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Device;
import esir.dom11.nsoc.model.device.Sensor;

import java.util.Properties;

public abstract class DAOFactory {

    /*
     * Attributes
     */

    protected ConnectionDb _connectionDb;
    protected HelperSetup _helperSetup;

    /*
     * Class Methods
     */

    public static DAOFactory getFactory(Properties dbProperties){
        FactoryType type = FactoryType.valueOf(dbProperties.getProperty("type"));
        if (type.equals(FactoryType.DAO_MYSQL)) {
            return new DAOFactoryMySQL(dbProperties);
        } else if (type.equals(FactoryType.DAO_MONGODB)) {
            return new DAOFactoryMongoDb(dbProperties);
        } else if (type.equals(FactoryType.DAO_SQLITE)) {
            return new DAOFactorySQLite(dbProperties);
        }
        return null;
    }

    /*
     * Abstract Methods
     */

    public abstract ActionDAO getActionDAO();

    public abstract CommandDAO getCommandDAO();

    public abstract DataDAO getDataDAO();

    public abstract DeviceDAO getDeviceDAO();

    public abstract LogDAO getLogDAO();

    public abstract TaskDAO getTaskDAO();

    public abstract UserDAO getUserDAO();


    /*
     * Getters / Setters
     */

    public ConnectionDb getConnectionDb() {
        return _connectionDb;
    }

    public HelperSetup getHelperSetup() {
        return _helperSetup;
    }

    /*
     * Abstract Methods
     */

    public DAO getDAO(Class daoClass) {
        if(daoClass.equals(Action.class)) {
            return getActionDAO();
        } else if(daoClass.equals(Command.class)) {
            return getCommandDAO();
        } else if(daoClass.equals(Data.class)) {
            return getDataDAO();
        } else if(daoClass.equals(Device.class) || daoClass.equals(Sensor.class) || daoClass.equals(Actuator.class)) {
            return getDeviceDAO();
        } else if(daoClass.equals(Log.class)) {
            return getLogDAO();
        } else if(daoClass.equals(Task.class)) {
            return getTaskDAO();
        } else if(daoClass.equals(User.class)) {
            return getUserDAO();
        }
        return null;
    }
}

