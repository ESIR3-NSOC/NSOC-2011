package esir.dom11.nsoc.datactrl.dao.model.mongodb;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMongoDb;
import esir.dom11.nsoc.datactrl.dao.dao.DataDAO;
import esir.dom11.nsoc.model.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class DataDAOMongoDb implements DataDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(DataDAOMongoDb.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMongoDb _connection;

    /*
     * Constructors
     */

    public DataDAOMongoDb(ConnectionDbMongoDb connectionDbMongoDb) {
        _connection = connectionDbMongoDb;
    }

    /*
     * Overrides
     */

    @Override
    public LinkedList<Data> findByDate(Date startDate, Date endDate, String role) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LinkedList<Data> findByDateAndStep(Date startDate, Date endDate, String role, int step) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LinkedList<Data> findByDateAndDataMax(Date startDate, Date endDate, String role, int datMax) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Data create(Data data) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Data retrieve(UUID uuid) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Data update(Data data) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
