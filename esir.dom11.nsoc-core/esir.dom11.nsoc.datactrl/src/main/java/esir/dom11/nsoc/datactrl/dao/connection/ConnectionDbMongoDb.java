package esir.dom11.nsoc.datactrl.dao.connection;

import com.mongodb.DB;
import com.mongodb.Mongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.Properties;

public class ConnectionDbMongoDb extends ConnectionDb {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ConnectionDbMongoDb.class.getName());

    /*
     * Attributes
     */
    
    private DB _db;

    /*
     * Constructors
     */

    public ConnectionDbMongoDb(Properties properties) {
        super(properties);
    }

    /*
     * Overrides
     */

    @Override
    public void connect() {
        Mongo m = null;
        try {
            System.out.println("connecting to mongo db...");
            m = new Mongo( _properties.getProperty("url") , Integer.valueOf(_properties.getProperty("port")) );
            System.out.println("Mongo db...");
            _db = m.getDB( _properties.getProperty("name") );
            System.out.println("connected and authenticate to mongo db success!!");
        } catch (UnknownHostException exception) {
            System.out.println("connection to mongo db ERROR!!");
            logger.error("Error MongoDb: " + exception.getMessage());
        }
    }

    @Override
    public void disconnect() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
