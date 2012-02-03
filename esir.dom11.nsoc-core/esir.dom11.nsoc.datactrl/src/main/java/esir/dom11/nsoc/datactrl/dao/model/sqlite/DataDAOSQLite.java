package esir.dom11.nsoc.datactrl.dao.model.sqlite;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbSQLite;
import esir.dom11.nsoc.datactrl.dao.dao.DataDAO;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactorySQLite;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class DataDAOSQLite implements DataDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(DataDAOSQLite.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbSQLite _connection;
    private DAOFactorySQLite _daoFactory;

    /*
     * Constructors
     */

    public DataDAOSQLite(ConnectionDbSQLite connectionDbSQLite, DAOFactorySQLite daoFactorySQLite) {
        _connection = connectionDbSQLite;
        _daoFactory = daoFactorySQLite;
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
                    .createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM datas WHERE id = '" + id + "'");
            if(result.next()) {
                DateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
                data = new Data(
                        id,
                        _daoFactory.getDeviceDAO().retrieve(UUID.fromString(result.getString("id_device"))),
                        result.getDouble("value"),
                        df.parse(result.getString("date")));
            }
        } catch (SQLException exception) {
            logger.error("Data retrieve error", exception);
            System.out.println("Data retrieve error"+ exception.getMessage());
        } catch (ParseException exception) {
            logger.error("Data retrieve error", exception);
            System.out.println("Data retrieve error" + exception.getMessage());
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
                    .createStatement()
                    .executeUpdate("DELETE FROM datas WHERE id = '" + id + "'");
            System.out.println("Data delete success");
            return true;
        } catch (SQLException exception) {
            logger.error("Data delete error",exception);
            System.out.println("Data delete error"+ exception.getMessage());
        }
        return false;
    }

    @Override
    public LinkedList<Data> findByDate(Date startDate, Date endDate, String location) {
        LinkedList<Data> dataList = new LinkedList<Data>();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT da.id, da.id_device, value, date FROM datas da " +
                            "JOIN devices de ON da.id_device=de.id " +
                            "WHERE da.date>'" + new Timestamp(startDate.getTime()) + "' " +
                            "AND de.location='" + location + "' " +
                            "AND date<'" + new Timestamp(endDate.getTime()) + "' ");
            System.out.println(new Timestamp(endDate.getTime()));
            DateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
            while (result.next()) {
                dataList.add(new Data(UUID.fromString(result.getString(1)),
                        _daoFactory.getDeviceDAO().retrieve(UUID.fromString(result.getString(2))),
                        result.getDouble(3),df.parse(result.getString(4))));
            }
        } catch (SQLException exception) {
            logger.error("Data find by date error", exception);
        } catch (ParseException exception) {
            logger.error("Data find by date error", exception);
        }
        return dataList;
    }

    @Override
    public LinkedList<Data> findByDateAndStep(Date startDate, Date endDate, String role, int step) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LinkedList<Data> findByDateAndDataMax(Date startDate, Date endDate, String role, int datMax) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
