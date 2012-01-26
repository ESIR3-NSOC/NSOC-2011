package esir.dom11.nsoc.datactrl.dao.model.sqlite;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbSQLite;
import esir.dom11.nsoc.datactrl.dao.dao.UserDAO;
import esir.dom11.nsoc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAOSQLite implements UserDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(UserDAOSQLite.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbSQLite _connection;

    /*
     * Constructors
     */

    public UserDAOSQLite(ConnectionDbSQLite connectionDbSQLite) {
        _connection = connectionDbSQLite;
    }

    /*
     * Overrides
     */

    @Override
    public User create(User user) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User retrieve(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User update(User user) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean delete(String s) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
