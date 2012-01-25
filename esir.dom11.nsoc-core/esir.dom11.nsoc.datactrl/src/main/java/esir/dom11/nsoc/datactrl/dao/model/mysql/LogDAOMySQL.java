package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.LogDAO;
import esir.dom11.nsoc.model.Log;
import esir.dom11.nsoc.model.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class LogDAOMySQL implements LogDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(LogDAOMySQL.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMySQL _connection;

    /*
    * Constructors
    */

    public LogDAOMySQL(ConnectionDbMySQL connectionDbMySQL) {
        _connection = connectionDbMySQL;
    }

    /*
     * Overrides
     */

    @Override
    public Log create(Log log) {
        Log newLog = retrieve(log.getId());
        if (newLog.getId().toString().compareTo("00000000-0000-0000-0000-000000000000")==0) {
            try {
                String statement = "INSERT INTO logs (id, date, `from`, message, log_level)"
                        + " VALUES('" + log.getId() + "',"
                        + " '" + new Timestamp(log.getDate().getTime()) + "',"
                        + " '" + log.getFrom() + "',"
                        + " '" + log.getMessage() + "',"
                        + " '" + log.getLogLevel() + "')";
                PreparedStatement prepare = _connection.getConnection()
                        .prepareStatement(statement);
                prepare.executeUpdate();
                newLog = retrieve(log.getId());
            } catch (SQLException exception) {
                System.out.println("Log insert error"+ exception);
            }
        }
        return newLog;
    }

    @Override
    public Log retrieve(UUID id) {
        Log log = new Log();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM logs WHERE id = '" + id + "'");
            if(result.first()) {
                log = new Log(id, result.getDate("date"), result.getString("from"), result.getString("message"), LogLevel.valueOf(result.getString("log_level")));
            }
        } catch (SQLException exception) {
            logger.error("Log retrieve error", exception);
        }
        return log;
    }

    @Override
    public Log update(Log log) {
        return retrieve(log.getId());
    }

    @Override
    public boolean delete(UUID id) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate("DELETE FROM logs WHERE id = '" + id + "'");
            return true;
        } catch (SQLException exception) {
            logger.error("Log delete error",exception);
        }
        return false;
    }
}
