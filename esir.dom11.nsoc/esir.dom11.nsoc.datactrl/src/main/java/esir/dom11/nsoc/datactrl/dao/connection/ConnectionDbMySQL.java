package esir.dom11.nsoc.datactrl.dao.connection;

// Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDbMySQL extends ConnectionDb {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ConnectionDbMySQL.class.getName());


    /*
     *  Constructors
     */

    public ConnectionDbMySQL(Properties properties) {
        super(properties);
    }

    /*
     * Overrides
     */

    @Override
    public void connect() {
        try{
            new com.mysql.jdbc.Driver();
            _connection = java.sql.DriverManager.getConnection(
                    //_properties.getProperty("url") + "/" + _properties.getProperty("name") + "?user=" + _properties.getProperty("user") + "&password=" + _properties.getProperty("pwd"));
                    "jdbc:mysql://localhost/nsoc11?user=root&password=");
                    logger.info("database connect success");
        } catch(Exception exception){
            logger.error("Database connect error", exception);
        }
    }

    @Override
    public void disconnect() {
        try {
            _connection.close();
            logger.info("Database disconnect success");
        } catch (SQLException exception) {
            logger.error("Database disconnect error", exception);
        }
    }
}
