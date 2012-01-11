package esir.dom11.nsoc.datactrl.dao.connection;

// Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

public abstract class ConnectionDb {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ConnectionDb.class.getName());

    /*
    * Attributes
    */

    protected Connection _connection;
    protected Properties _properties;

    /*
     * Constructors
     */

    public ConnectionDb(Properties properties) {
        _properties = properties;
    }

    /*
     * Getter / Setter
     */

    public Connection getConnection() {
        return _connection;
    }

    /*
     * Methods
     */

    abstract public void connect();

    abstract public void disconnect();
}
