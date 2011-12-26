package esir.dom11.nsoc.datactrl.dao.factory;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.TaskDAO;
import esir.dom11.nsoc.datactrl.dao.dao.UserDAO;
import esir.dom11.nsoc.datactrl.dao.model.mysql.TaskDAOMySQL;
import esir.dom11.nsoc.datactrl.dao.model.mysql.UserDAOMySQL;

import java.util.Properties;

public class DAOFactoryMySQL extends DAOFactory {

    protected DAOFactoryMySQL(Properties dbProperties) {
        _connectionDb = new ConnectionDbMySQL(dbProperties);
        _connectionDb.connect();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOMySQL((ConnectionDbMySQL)_connectionDb);
    }

    @Override
    public TaskDAO getTaskDAO() {
        return new TaskDAOMySQL((ConnectionDbMySQL)_connectionDb);
    }
}
