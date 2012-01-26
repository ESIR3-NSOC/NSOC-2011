package esir.dom11.nsoc.datactrl.dao.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDbSQLite extends ConnectionDb {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ConnectionDbSQLite.class.getName());


    /*
     *  Constructors
     */

    public ConnectionDbSQLite(Properties properties) {
        super(properties);
    }

    /*
     * Overrides
     */

    @Override
    public void connect() {
        try{
            new org.sqlite.JDBC();
            _connection = java.sql.DriverManager.getConnection(
                    "jdbc:sqlite:" + _properties.getProperty("name") );
            logger.info("Database connect success");
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
