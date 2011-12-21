package esir.dom11.nsoc.datactrl.dao.factory;


import esir.dom11.nsoc.datactrl.dao.DAO;
import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.model.mysql.UserDAOMySQL;
import esir.dom11.nsoc.model.User;

import java.util.Properties;

public class DAOFactoryMySQL extends DAOFactory {

    protected DAOFactoryMySQL(Properties dbProperties) {
        _connectionDb = new ConnectionDbMySQL(dbProperties);
        _connectionDb.connect();
    }

    @Override
    public DAO<String,User> getUserDAO() {
        return new UserDAOMySQL((ConnectionDbMySQL)_connectionDb);
    }
}
