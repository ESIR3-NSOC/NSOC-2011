package esir.dom11.nsoc.datactrl.dao.model.db4o;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbDb4o;
import esir.dom11.nsoc.datactrl.dao.dao.UserDAO;
import esir.dom11.nsoc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAODb4o implements UserDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(UserDAODb4o.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbDb4o _connection;

    /*
     * Constructors
     */

    public UserDAODb4o(ConnectionDbDb4o connectionDbDb4o) {
        _connection = connectionDbDb4o;
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
