package esir.dom11.nsoc.datactrl.dao.model.mongodb;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMongoDb;
import esir.dom11.nsoc.datactrl.dao.dao.UserDAO;
import esir.dom11.nsoc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAOMongoDb implements UserDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(UserDAOMongoDb.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMongoDb _connection;

    /*
     * Constructors
     */

    public UserDAOMongoDb(ConnectionDbMongoDb connectionDbMongoDb) {
        _connection = connectionDbMongoDb;
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
