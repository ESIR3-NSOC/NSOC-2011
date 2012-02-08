package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.ActionDAO;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryMySQL;
import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ActionDAOMySQL implements ActionDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ActionDAOMySQL.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMySQL _connection;
    private DAOFactoryMySQL _daoFactoryMySQL;

    /*
     * Constructors
     */

    public ActionDAOMySQL(ConnectionDbMySQL connectionDbMySQL, DAOFactoryMySQL daoFactoryMySQL) {
        _connection = connectionDbMySQL;
        _daoFactoryMySQL = daoFactoryMySQL;
    }

    /*
     * Overrides
     */

    @Override
    public Action create(Action action) {
        Action newAction = retrieve(action.getId());
        if (newAction.getId().toString().compareTo("00000000-0000-0000-0000-000000000000")==0) {
            Device device = _daoFactoryMySQL.getDeviceDAO().create(action.getActuator());

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
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM actions WHERE id = '" + id + "'");
            if(result.first()) {
                action = new Action(id,
                                (Actuator)_daoFactoryMySQL.getDeviceDAO().retrieve(UUID.fromString(result.getString("id_actuator"))),
                                result.getString("value"));
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
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate("DELETE FROM actions WHERE id = '" + id + "'");
            return true;
        } catch (SQLException exception) {
            logger.error("Action delete error",exception);
        }
        return false;
    }
}
