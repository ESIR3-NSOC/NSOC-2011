package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.DataDAO;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryMySQL;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    private DAOFactoryMySQL _daoFactory;

    /*
     * Constructors
     */

    public DataDAOMySQL(ConnectionDbMySQL connectionDbMySQL, DAOFactoryMySQL daoFactoryMySQL) {
        _connection = connectionDbMySQL;
        _daoFactory = daoFactoryMySQL;
    }

    /*
     * Overrides
     */

    @Override
    public Data create(Data data) {
        Data newData = retrieve(data.getId());
        if (newData.getId().toString().compareTo("00000000-0000-0000-0000-000000000000")==0) {
            try {
                String statement = "INSERT INTO datas (id, id_device, value, date)"
                        + " VALUES('" + data.getId() + "',"
                        + " '" + data.getDevice().getId() + "',"
                        + " '" + data.getValue() + "',"
                        + " '" + new Timestamp(data.getDate().getTime()) + "')";
                PreparedStatement prepare = _connection.getConnection()
                        .prepareStatement(statement);
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
                data = new Data(
                        id,
                        _daoFactory.getDeviceDAO().retrieve(UUID.fromString(result.getString("id_device"))),
                        result.getDouble("value"),
                        result.getDate("date"));
            }
        } catch (SQLException exception) {
            logger.error("Data retrieve error", exception);
        }
        return data;
    }

    /**
     * No change for update data
     */
    @Override
    public Data update(Data data) {
        return retrieve(data.getId());
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
    public LinkedList<Data> findByDate(Date startDate, Date endDate, String location, DataType dataType) {
        LinkedList<Data> dataList = new LinkedList<Data>();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM datas da " +
                                    "JOIN devices de ON da.id_device=de.id " +
                                    "WHERE da.date>'" + new Timestamp(startDate.getTime()) + "' " +
                                        "AND de.data_type='" + dataType.getValue() + "' " +
                                        "AND de.location='" + location + "' " +
                                        "AND date<'" + new Timestamp(endDate.getTime()) + "' ");
            System.out.println(new Timestamp(endDate.getTime()));
            result.beforeFirst();
            while (result.next()) {
                dataList.add(new Data(UUID.fromString(result.getString("da.id")),
                                        _daoFactory.getDeviceDAO().retrieve(UUID.fromString(result.getString("da.id_device"))),
                                        result.getDouble("value"),result.getDate("date")));
            }
        } catch (SQLException exception) {
            logger.error("Data find by date error", exception);
        }
        return dataList;
    }

    @Override
    public LinkedList<Data> findByDateAndStep(Date startDate, Date endDate, String role, int step) {
        LinkedList<Data> dataList = new LinkedList<Data>();
        return dataList;
    }

    @Override
    public LinkedList<Data> findByDateAndDataMax(Date startDate, Date endDate, String role, int datMax) {
        LinkedList<Data> dataList = new LinkedList<Data>();
        return dataList;
    }
}


/*

// min time between row
select id, role, date, value, TO_SECONDS(current_datas.date) - TO_SECONDS((select date from datas prev_datas where current_datas.date>prev_datas.date order by prev_datas.date DESC limit 0,1))
from datas current_datas
WHERE (select date from datas prev_datas where current_datas.date>prev_datas.date order by prev_datas.date DESC limit 0,1) IS NULL
OR TO_SECONDS(current_datas.date) - TO_SECONDS((select date from datas prev_datas where current_datas.date>prev_datas.date order by prev_datas.date DESC limit 0,1)) >=7200
order by current_datas.date

*/