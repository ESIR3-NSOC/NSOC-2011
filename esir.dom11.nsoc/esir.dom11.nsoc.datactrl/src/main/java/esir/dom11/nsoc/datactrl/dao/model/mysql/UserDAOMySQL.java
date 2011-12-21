package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.DAO;
import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOMySQL implements DAO<String,User> {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(UserDAOMySQL.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMySQL _connection;

    /*
     * Constructors
     */

    public UserDAOMySQL(ConnectionDbMySQL connectionDbMySQL) {
        _connection = connectionDbMySQL;
    }

    /*
     * Overrides
     */

    @Override
    public User create(User user) {
        User newUser = retrieve(user.getId());
        if (newUser.getId()==null) {
            try {
                PreparedStatement prepare = _connection.getConnection()
                    .prepareStatement(
                            "INSERT INTO users (id, pwd) VALUES('" + user.getId() + "', '" + user.getPwd() + "')"
                    );
                prepare.executeUpdate();
                newUser = retrieve(user.getId());
                logger.info("User insert success: " + user.toString());
            } catch (SQLException exception) {
                logger.error("User insert error", exception);
            }
        }
        return newUser;
    }

    @Override
    public User retrieve(String id) {
        User user = new User();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM users WHERE id = '" + id + "'");
            if(result.first()) {
                user = new User(id,result.getString("pwd"));
            }
        } catch (SQLException exception) {
            logger.error("User retrieve error", exception);
        }
        return user;
    }

    @Override
    public User update(User user) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate(
                    "UPDATE users SET pwd = '" + user.getPwd() + "'"+
                            " WHERE id = '" + user.getId() + "'"
            );

            user = this.retrieve(user.getId());
        } catch (SQLException exception) {
            logger.error("User update error",exception);
        }
        return user;
    }

    @Override
    public boolean delete(String id) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate("DELETE FROM users WHERE id = '" + id + "'");
             return true;
        } catch (SQLException exception) {
            logger.error("User delete error",exception);
        }
        return false;
    }
}
