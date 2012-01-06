package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.DataDAO;
import esir.dom11.nsoc.model.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public LinkedList<Data> findByDate(Date startDate, Date endDate, UUID idSensor) {
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
