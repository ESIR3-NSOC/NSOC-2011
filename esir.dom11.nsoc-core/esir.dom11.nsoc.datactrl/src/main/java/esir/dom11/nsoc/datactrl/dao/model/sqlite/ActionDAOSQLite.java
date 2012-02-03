package esir.dom11.nsoc.datactrl.dao.model.sqlite;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbSQLite;
import esir.dom11.nsoc.datactrl.dao.dao.ActionDAO;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactorySQLite;
import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ActionDAOSQLite implements ActionDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ActionDAOSQLite.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbSQLite _connection;
    private DAOFactorySQLite _daoFactorySQLite;

    /*
     * Constructors
     */

    public ActionDAOSQLite(ConnectionDbSQLite connectionDbSQLite, DAOFactorySQLite daoFactorySQLite) {
        _connection = connectionDbSQLite;
        _daoFactorySQLite = daoFactorySQLite;
    }

    /*
     * Overrides
     */

    @Override
    public Action create(Action action) {
        Action newAction = retrieve(action.getId());
        if (newAction.getId()==null) {
            Device device = _daoFactorySQLite.getDeviceDAO().create(action.getActuator());
            if (device.getId().toString().compareTo("00000000-0000-0000-0000-000000000000")!=0) {
                try {
                    PreparedStatement prepare = _connection.getConnection()
                            .prepareStatement(
                                    "INSERT INTO actions (id, id_actuator, value)"
                                            + " VALUES('" + action.getId() + "',"
                                            + " '" + action.getActuator().getId() + "',"
                                            + " '" + action.getValue() + "')"
                            );
                    prepare.executeUpdate();
                    newAction = retrieve(action.getId());
                } catch (SQLException exception) {
                    logger.error("Action insert error", exception);
                }
            }
        }
        return newAction;
    }

    @Override
    public Action retrieve(UUID id) {
        Action action = new Action();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM actions WHERE id = '" + id + "'");
            if(result.next()) {
                action = new Action(id,
                        (Actuator)_daoFactorySQLite.getDeviceDAO().retrieve(UUID.fromString(result.getString("id_actuator"))),
                        result.getDouble("value"));
            }
        } catch (SQLException exception) {
            logger.error("Action retrieve error", exception);
        }
        return action;
    }

    @Override
    public Action update(Action action) {
        return  retrieve(action.getId());
    }

    @Override
    public boolean delete(UUID id) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY)
                    .executeUpdate("DELETE FROM actions WHERE id = '" + id + "'");
            return true;
        } catch (SQLException exception) {
            logger.error("Action delete error",exception);
        }
        return false;
    }
}
