package esir.dom11.nsoc.datactrl.dao.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ConnectionDbDb4o extends ConnectionDb {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ConnectionDbDb4o.class.getName());


    /*
     *  Constructors
     */

    public ConnectionDbDb4o(Properties properties) {
        super(properties);
    }

    /*
     * Overrides
     */

    @Override
    public void connect() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void disconnect() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
