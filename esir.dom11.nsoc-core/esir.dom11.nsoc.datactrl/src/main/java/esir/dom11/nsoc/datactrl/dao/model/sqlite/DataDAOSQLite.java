package esir.dom11.nsoc.datactrl.dao.model.sqlite;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbSQLite;
import esir.dom11.nsoc.datactrl.dao.dao.DataDAO;
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

public class DataDAOSQLite implements DataDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(DataDAOSQLite.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbSQLite _connection;

    /*
     * Constructors
     */

    public DataDAOSQLite(ConnectionDbSQLite connectionDbSQLite) {
        _connection = connectionDbSQLite;
    }

    /*
     * Overrides
     */

    @Override
    public Data create(Data data) {
        Data newData = retrieve(data.getId());
        if (newData.getId().toString().compareTo("00000000-0000-0000-0000-000000000000")==0) {
            try {
                String statement = "INSERT INTO datas (id, data_type, location, value, date)"
                        + " VALUES('" + data.getId() + "',"
                        + " '" + data.getDataType().getValue() + "',"
                        + " '" + data.getLocation() + "',"
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
                data = new Data(id, DataType.valueOf(result.getString("data_type")),
                        result.getString("location"),result.getDouble("value"),result.getDate("date"));
            }
        } catch (SQLException exception) {
            logger.error("Data retrieve error", exception);
            System.out.println("Data retrieve error"+ exception.getMessage());
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
}
