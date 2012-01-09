package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.DataDAO;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class DataDAOMySQL implements DataDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(DataDAOMySQL.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMySQL _connection;

    /*
     * Constructors
     */

    public DataDAOMySQL(ConnectionDbMySQL connectionDbMySQL) {
        _connection = connectionDbMySQL;
    }

    /*
     * Overrides
     */

    @Override
    public Data create(Data data) {
        Data newData = retrieve(data.getId());
        if (newData.getId()==null) {
            try {
                PreparedStatement prepare = _connection.getConnection()
                        .prepareStatement(
                                "INSERT INTO datas (id, data_type, id_sensor, value, date)"
                                        + " VALUES('" + data.getId() + "',"
                                        + " '" + data.getDataType().getValue() + "',"
                                        + " '" + data.getIdSensor() + "',"
                                        + " '" + data.getValue() + "',"
                                        + " '" + new java.sql.Date(data.getDate().getTime()) + "')"
                        );
                prepare.executeUpdate();
                newData = retrieve(data.getId());
            } catch (SQLException exception) {
                System.out.println("Data insert error"+ exception);
            }
        }
        return newData;
    }

    @Override
    public Data retrieve(UUID id) {
        Data data = new Data();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM datas WHERE id = '" + id + "'");
            if(result.first()) {
                data = new Data(id, DataType.valueOf(result.getString("data_type")),
                        UUID.fromString(result.getString("id_sensor")),result.getDouble("value"),result.getDate("date"));
            }
        } catch (SQLException exception) {
            logger.error("Data retrieve error", exception);
        }
        return data;
    }

    @Override
    public Data update(Data data) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate(
                            "UPDATE datas SET data_type = '" + data.getDataType() + "',"
                                    + "id_sensor = '" + data.getIdSensor() + "',"
                                    + "value = '" + data.getValue() + "',"
                                    + "date = '" + data.getDate() + "'"
                    );

            data = this.retrieve(data.getId());
        } catch (SQLException exception) {
            logger.error("Data update error",exception);
        }
        return data;
    }

    @Override
    public boolean delete(UUID id) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate("DELETE FROM datas WHERE id = '" + id + "'");
            return true;
        } catch (SQLException exception) {
            logger.error("Data delete error",exception);
        }
        return false;
    }

    @Override
    public LinkedList<Data> findByDate(Date startDate, Date endDate, UUID idSensor) {
        LinkedList<Data> dataList = new LinkedList<Data>();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM datas " +
                                    "WHERE date>'" + startDate + "' " +
                                        "AND id_sensor='" + idSensor + "' " +
                                        "AND date<'" + endDate + "' ");
            result.beforeFirst();
            while (result.next()) {
                dataList.add(new Data(UUID.fromString(result.getString("id")),
                                        DataType.valueOf(result.getString("data_type")),
                                        UUID.fromString(result.getString("id_sensor")),
                                        result.getDouble("value"),result.getDate("date")));
            }
        } catch (SQLException exception) {
            logger.error("Data find by date error", exception);
        }
        return dataList;
    }
}
