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
        if (newCommand.getId().toString().compareTo("00000000-0000-0000-0000-000000000000")==0) {
            try {
                PreparedStatement prepare = _connection.getConnection()
                        .prepareStatement("INSERT INTO commands (id, category, `lock`, time_out)"
                                + " VALUES('" + command.getId() + "',"
                                + " '" + command.getCategory() + "',"
                                + " '" + command.getLock() + "',"
                                + " '" + command.getTimeOut() + "')");
                prepare.executeUpdate();
            } catch (SQLException exception) {
                logger.error("Command insert error", exception);
            }

            for (Action action : command.getActionList()) {
                if (_daoFactory.getActionDAO().create(action).toString().compareTo("00000000-0000-0000-0000-000000000000")!=0) {
                    try {
                        PreparedStatement prepare = _connection.getConnection()
                                .prepareStatement("INSERT INTO commands_actions (id_command, id_action) " +
                                        "VALUES ('" + command.getId() + "', '" + action.getId() + "')");
                        prepare.executeUpdate();
                    } catch (SQLException exception) {
                        logger.error("Command insert error (action list): ", exception);
                    }
                }
            }
            newCommand = retrieve(command.getId());
        }
        return newCommand;
    }

    @Override
    public Command retrieve(UUID id) {
        Command command = new Command();
        try {
            ResultSet result = _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT id_command, id_action, category, `lock`, time_out " +
                                    "FROM commands c " +
                                    "JOIN commands_actions ca ON c.id=ca.id_command " +
                                    "WHERE c.id = '" + id + "'");
            result.beforeFirst();
            LinkedList<Action> actionList = new LinkedList<Action>();
            while (result.next()) {
                actionList.add(_daoFactory.getActionDAO().retrieve(UUID.fromString(result.getString(2))));
            }

            if(result.first()) {
                command = new Command(id,actionList,Category.valueOf(result.getString(3)),
                                        result.getLong(4),
                                        result.getLong(5));
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
                        exist = true;
                        result.deleteRow();
                    }
                }

                if (!exist) {
                    if (_daoFactory.getActionDAO().create(action) != null) {
                        _connection.getConnection()
                                .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                                .executeUpdate("INSERT INTO commands_actions (id_command, id_action)"
                                        + " VALUES('" + command.getId() + "',"
                                        + " '" + action.getId() + "')");
                    }
                }
            }

            // Delete others
            result.beforeFirst();
            while (result.next()) {
                _connection.getConnection()
                        .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                        .executeUpdate("DELETE FROM commands_actions " +
                                "WHERE id_command='"+ command.getId() +"' " +
                                "AND id_action='" + result.getString("id_action") + "';");
            }

            command = this.retrieve(command.getId());
        } catch (SQLException exception) {
            logger.error("Command update error",exception);
        }
        return command;
    }

    @Override
    public boolean delete(UUID id) {
        Command command = retrieve(id);
        try {
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate("DELETE FROM commands_actions WHERE id_command = '" + command.getId() + "'");
            _connection.getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeUpdate("DELETE FROM commands WHERE id = '" + command.getId() + "'");

            for (Action action : command.getActionList()) {
                _daoFactory.getActionDAO().delete(action.getId());
            }
            return true;
        } catch (SQLException exception) {
            logger.error("Command delete error",exception);
        }

        return false;
    }
}
