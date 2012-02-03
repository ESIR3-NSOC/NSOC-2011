package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.DeviceDAO;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Device;
import esir.dom11.nsoc.model.device.Sensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DeviceDAOMySQL  implements DeviceDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(DeviceDAOMySQL.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMySQL _connection;

    /*
     * Constructors
     */

    public DeviceDAOMySQL(ConnectionDbMySQL connectionDbMySQL) {
        _connection = connectionDbMySQL;
    }

    /*
     * Overrides
     */
    @Override
    public Device create(Device device) {
        Device newDevice = retrieve(device.getId());
        if (newDevice.getId().toString().compareTo("00000000-0000-0000-0000-000000000000")==0) {
            try {
                String statement = "INSERT INTO devices (id, data_type, location, device_type)"
                        + " VALUES('" + device.getId() + "',"
                        + " '" + device.getDataType().getValue() + "',"
                        + " '" + device.getLocation() + "',"
                        + " '" + device.getClass().getName() + "')";
                PreparedStatement prepare = _connection.getConnection()
                        .prepareStatement(statement);
                prepare.executeUpdate();
                newDevice = retrieve(device.getId());
            } catch (SQLException exception) {
                System.out.println("Device insert error"+ exception);
            }
        }
        return newDevice;
    }

    @Override
    public Device retrieve(UUID id) {
        Device device = new Actuator();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM devices WHERE id='" + id + "'");
            if(result.first()) {
                if (result.getString("device_type").compareTo("esir.dom11.nsoc.model.device.Actuator")==0) {
                    device = new Actuator(id, DataType.valueOf(result.getString("data_type")),
                                result.getString("location"));
                } else if (result.getString("device_type").compareTo("esir.dom11.nsoc.model.device.Sensor")==0) {
                    device = new Sensor(id, DataType.valueOf(result.getString("data_type")),
                            result.getString("location"));
                }
            }
        } catch (SQLException exception) {
            logger.error("Device retrieve error", exception);
        }


        return device;
    }

    @Override
    public Device update(Device device) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean delete(UUID id) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate("DELETE FROM devices WHERE id = '" + id + "'");
            return true;
        } catch (SQLException exception) {
            logger.error("Device delete error",exception);
        }
        return false;
    }
}
