package esir.dom11.nsoc.datactrl.dao.model.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMongoDb;
import esir.dom11.nsoc.datactrl.dao.dao.DataDAO;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryDb4o;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryMongoDb;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.device.Sensor;
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
    private DAOFactoryMongoDb _daoFactory;

    /*
     * Constructors
     */

    public DataDAOMongoDb(ConnectionDbMongoDb connectionDbMongoDb, DAOFactoryMongoDb daoFactoryMongoDb) {
        _connection = connectionDbMongoDb;
        _daoFactory = daoFactoryMongoDb;
    }

    /*
     * Overrides
     */

    @Override
    public LinkedList<Data> findByDate(Date startDate, Date endDate, String location, DataType dataType) {
        return null;
    }

    @Override
    public LinkedList<Data> findByDateAndStep(Date startDate, Date endDate, String location, int step) {
        return null;
    }

    @Override
    public LinkedList<Data> findByDateAndDataMax(Date startDate, Date endDate, String location, int datMax) {
        return null;
    }

    @Override
    public Data create(Data data) {
        System.out.println("Create data...");
        Data retrieveData = retrieve(data.getId());
        if (retrieveData==null) {
            System.out.println("Create new Data");
            DBCollection datasCollection = _connection.getDb().getCollection("datas");

            BasicDBObject saveData = new BasicDBObject();

            saveData.put("id", data.getId().toString());
            saveData.put("id_device", data.getSensor().getId());
            saveData.put("date", data.getDate().toString());
            saveData.put("value", data.getValue());

        System.out.println("data prepared");
            datasCollection.insert(saveData);

        System.out.println("Data insert");
            return retrieve(data.getId());
        }
        return retrieveData;
    }

    @Override
    public Data retrieve(UUID id) {
        DBCollection datasCollection = _connection.getDb().getCollection("datas");
        BasicDBObject query = new BasicDBObject();
        System.out.println(datasCollection.getCount());
        System.out.println("collection= " + datasCollection.getCount());

        query.put("id", id.toString());

        DBCursor cur = datasCollection.find(query);
        System.out.println("cursor= " + cur.count());
        if (cur.hasNext()) {
            DBObject mongoData = cur.next();
            return new Data(
                    id,
                    (Sensor)_daoFactory.getDeviceDAO().retrieve(UUID.fromString((String)mongoData.get("id_device"))),
                    (String)mongoData.get("value"),
                    (Date)mongoData.get("date"));
        }
        return null;
    }

    @Override
    public Data update(Data data) {
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        DBCollection datasCollection = _connection.getDb().getCollection("datas");
        BasicDBObject query = new BasicDBObject();
        query.put("id", id);
        DBCursor cur = datasCollection.find(query);
        if (cur.length()==1) {
            datasCollection.remove(cur.next());
            return true;
        }
        return false;
    }
}
