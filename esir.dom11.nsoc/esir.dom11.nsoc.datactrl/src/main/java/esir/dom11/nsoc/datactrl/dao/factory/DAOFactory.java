package esir.dom11.nsoc.datactrl.dao.factory;

import esir.dom11.nsoc.datactrl.dao.dao.DAO;
import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDb;
import esir.dom11.nsoc.datactrl.dao.dao.TaskDAO;
import esir.dom11.nsoc.datactrl.dao.dao.UserDAO;
import esir.dom11.nsoc.model.Task;
import esir.dom11.nsoc.model.User;

import java.util.Properties;
import java.util.UUID;

public abstract class DAOFactory {

    /*
     * Attributes
     */

    protected ConnectionDb _connectionDb;

    /*
     * Class Methods
     */

    public static DAOFactory getFactory(Properties dbProperties){
        FactoryType type = FactoryType.valueOf(dbProperties.getProperty("type"));
        if(type.equals(FactoryType.DAO_MYSQL)) {
            return new DAOFactoryMySQL(dbProperties);
        }
        return null;
    }

    /*
     * Abstract Methods
     */
    
    public abstract UserDAO getUserDAO();

    public abstract TaskDAO getTaskDAO();

    /*
     * Getters / Setters
     */

    public ConnectionDb getConnectionDb() {
        return _connectionDb;
    }

    /*
     * Abstract Methods
     */

    public DAO getDAO(Class daoClass) {
        if(daoClass.equals(User.class)) {
            return getUserDAO();
        } else if(daoClass.equals(Task.class)) {
            return getTaskDAO();
        }
        return null;
    }
}

