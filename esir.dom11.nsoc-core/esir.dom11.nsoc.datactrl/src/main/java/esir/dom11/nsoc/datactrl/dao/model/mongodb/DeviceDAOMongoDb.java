package esir.dom11.nsoc.datactrl.dao.model.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMongoDb;
import esir.dom11.nsoc.datactrl.dao.dao.DeviceDAO;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryMongoDb;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Device;
import esir.dom11.nsoc.model.device.Sensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

public class DeviceDAOMongoDb implements DeviceDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(DeviceDAOMongoDb.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMongoDb _connection;

    /*
     * Constructors
     */

    public DeviceDAOMongoDb(ConnectionDbMongoDb connectionDbMongoDb) {
        _connection = connectionDbMongoDb;
    }

    /*
     * Overrides
     */
    @Override
    public Device create(Device device) {
        System.out.println("Create device...");
        Device retrieveDevice = retrieve(device.getId());
        if (retrieveDevice==null) {
            System.out.println("Create new Device");
            DBCollection deviceCollec = _connection.getDb().getCollection("devices");

            BasicDBObject saveDevice = new BasicDBObject();

            saveDevice.put("id", device.getId().toString());
            saveDevice.put("data_type", device.getDataType().getValue());
            saveDevice.put("location", device.getLocation());
            saveDevice.put("device_type", device.getClass().getName());

            System.out.println("device prepared");
            deviceCollec.insert(saveDevice);

            System.out.println("Device insert");
            return retrieve(device.getId());
        }
        return retrieveDevice;
    }

    @Override
    public Device retrieve(UUID id) {
        DBCollection deviceCollec = _connection.getDb().getCollection("devices");
        BasicDBObject query = new BasicDBObject();
        System.out.println(deviceCollec.getCount());
        System.out.println("collection= " + deviceCollec.getCount());

        query.put("id", id.toString());

        DBCursor cur = deviceCollec.find(query);
        System.out.println("cursor= " + cur.count());
        if (cur.hasNext()) {
            DBObject mongoData = cur.next();
            if (((String)mongoData.get("id_device")).compareTo("esir.dom11.nsoc.model.device.Actuator")==0) {
                return new Actuator(id, DataType.valueOf((String)mongoData.get("data_type")),
                        (String)mongoData.get("location"));
            } else if (((String)mongoData.get("id_device")).compareTo("esir.dom11.nsoc.model.device.Sensor")==0) {
                return new Sensor(id, DataType.valueOf((String)mongoData.get("data_type")),
                        (String)mongoData.get("location"));
            }
        }
        return null;
    }

    @Override
    public Device update(Device device) {
        return retrieve(device.getId());
    }

    @Override
    public boolean delete(UUID id) {
        DBCollection deviceCollec = _connection.getDb().getCollection("devices");
        BasicDBObject query = new BasicDBObject();
        query.put("id", id);
        DBCursor cur = deviceCollec.find(query);
        if (cur.length()==1) {
            deviceCollec.remove(cur.next());
            return true;
        }
        return false;
    }
}
