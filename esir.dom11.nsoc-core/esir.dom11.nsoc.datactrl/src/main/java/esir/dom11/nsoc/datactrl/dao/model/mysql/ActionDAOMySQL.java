package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.ActionDAO;
import esir.dom11.nsoc.model.Action;
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

    /*
     * Constructors
     */

    public ActionDAOMySQL(ConnectionDbMySQL connectionDbMySQL) {
        _connection = connectionDbMySQL;
    }

    /*
     * Overrides
     */

    @Override
    public Action create(Action action) {
        Action newAction = retrieve(action.getId());
        if (newAction.getId()==null) {
            try {
                PreparedStatement prepare = _connection.getConnection()
                        .prepareStatement(
                                "INSERT INTO actions (id, id_actuator, value)"
                                        + " VALUES('" + action.getId() + "',"
                                        + " '" + action.getIdActuator() + "',"
                                        + " '" + action.getValue() + "')"
                        );
                prepare.executeUpdate();
                newAction = retrieve(action.getId());
            } catch (SQLException exception) {
                logger.error("Action insert error", exception);
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
                action = new Action(id, UUID.fromString(result.getString("id_actuator")),
                                        result.getDouble("value"));
            }
        } catch (SQLException exception) {
            logger.error("Action retrieve error", exception);
        }
        return action;
    }

    @Override
    public Action update(Action action) {
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate(
                            "UPDATE actions SET id_actuator = '" + action.getIdActuator() + "',"
                                    + "value = '" + action.getValue() + "'"
                    );

            action = this.retrieve(action.getId());
        } catch (SQLException exception) {
            logger.error("Action update error",exception);
        }
        return action;
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
