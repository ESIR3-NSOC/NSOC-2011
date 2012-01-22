package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.connection.ConnectionDbMySQL;
import esir.dom11.nsoc.datactrl.dao.dao.CommandDAO;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactory;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryMySQL;
import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.Category;
import esir.dom11.nsoc.model.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class CommandDAOMySQL implements CommandDAO {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(CommandDAOMySQL.class.getName());

    /*
     * Attributes
     */

    private ConnectionDbMySQL _connection;
    private DAOFactoryMySQL _daoFactory;

    /*
     * Constructors
     */

    public CommandDAOMySQL(ConnectionDbMySQL connectionDbMySQL, DAOFactoryMySQL daoFactoryMySQL) {
        _connection = connectionDbMySQL;
        _daoFactory = daoFactoryMySQL;
    }

    /*
     * Overrides
     */

    @Override
    public Command create(Command command) {
        Command newCommand = retrieve(command.getId());
        if (newCommand.getId()==null) {

            StringBuilder statement = new StringBuilder();

            for (Action action : command.getActionList()) {
                if (_daoFactory.getActionDAO().create(action) != null) {
                    statement.append("INSERT INTO commands_actions (id_command, id_action)"
                            + " VALUES('" + command.getId() + "',"
                            + " '" + action.getId() + "');");
                }
            }

            /*_daoFactory.getCategoryDAO().create(command.getCategory());
            statement.append("INSERT INTO commands (id, id_category)"
                    + " VALUES('" + command.getId() + "',"
                    + " '" + command.getCategory().getId() + "')");  */

            try {
                PreparedStatement prepare = _connection.getConnection()
                                                .prepareStatement(statement.toString());
                prepare.executeUpdate();
                newCommand = retrieve(command.getId());
            } catch (SQLException exception) {
                logger.error("Command insert error", exception);
            }
        }
        return newCommand;
    }

    @Override
    public Command retrieve(UUID id) {
        Command command = new Command();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT id_command, id_action, id_category, name, lock, id_actuator, value, time_out " +
                                    "FROM commands c " +
                                    "JOIN categories cat ON c.id_category=cat.id " +
                                    "JOIN commands_actions ca ON c.id=ca.id_command " +
                                    "JOIN actions a ON ca.id_ac " +
                                    "WHERE c.id = '" + id + "'");
            result.beforeFirst();
            LinkedList<Action> actionList = new LinkedList<Action>();
            while (result.next()) {
                actionList.add(new Action(UUID.fromString(result.getString("id_action")),
                                        UUID.fromString(result.getString("id_actuator")),
                                        result.getDouble("value"),
                                        result.getInt("time_out")));
            }

            if(result.first()) {
                Category category = new Category(id, result.getString("name"),result.getInt("lock"));
                command = new Command(id,actionList,category);
            }
        } catch (SQLException exception) {
            logger.error("Command retrieve error", exception);
        }
        return command;
    }

    @Override
    public Command update(Command command) {
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM commands_actions WHERE id_command='" + command.getId() + "'");

            StringBuilder statement = new StringBuilder();

            // Update actions list
            for (Action action : command.getActionList()) {
                result.beforeFirst();
                boolean exist = false;
                while (result.next()) {
                    // if action exist => update
                    if (result.getString("id_action").compareTo(action.getId().toString())==0) {
                        _daoFactory.getActionDAO().update(action);
                        exist = true;
                        result.deleteRow();
                    }
                }

                if (!exist) {
                    if (_daoFactory.getActionDAO().create(action) != null) {
                        statement.append("INSERT INTO commands_actions (id_command, id_action)"
                                + " VALUES('" + command.getId() + "',"
                                + " '" + action.getId() + "');");
                    }
                }
            }

            // Delete others
            result.beforeFirst();
            while (result.next()) {
                statement.append("DELETE FROM commands_actions " +
                                    "WHERE id_command='"+ command.getId() +"' " +
                                    "AND id_action='" + result.getString("id_action") + "';");
            }

            //_daoFactory.getCategoryDAO().update(command.getCategory());

            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate(statement.toString());

            command = this.retrieve(command.getId());
        } catch (SQLException exception) {
            logger.error("Data update error",exception);
        }
        return command;
    }

    @Override
    public boolean delete(UUID id) {
        Command command = retrieve(id);
        StringBuilder statement = new StringBuilder();
        statement.append("DELETE FROM commands_actions WHERE id = '" + command.getId() + "';");
        statement.append("DELETE FROM commands WHERE id = '" + command.getId() + "';");
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate(statement.toString());

            for (Action action : command.getActionList()) {
                _daoFactory.getActionDAO().delete(action.getId());
            }
            return true;
        } catch (SQLException exception) {
            logger.error("Data delete error",exception);
        }

        return false;
    }
}
