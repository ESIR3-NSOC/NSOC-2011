package esir.dom11.nsoc.datactrl.helper;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryMySQL;
import esir.dom11.nsoc.model.*;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Device;
import esir.dom11.nsoc.model.device.Sensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class HelperSetupMySQL extends HelperSetup {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(HelperSetupMySQL.class.getName());

    /*
     * Constructors
     */

    public HelperSetupMySQL(DAOFactoryMySQL daoFactoryMySQL) {
        super(daoFactoryMySQL);
    }

    /*
     * Overrides
     */

    public void setupTable() {

        try {
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("SET SQL_MODE=\"NO_AUTO_VALUE_ON_ZERO\"");

            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("SET time_zone = \"+00:00\"");

            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("DROP TABLE IF EXISTS `commands_actions`, " +
                            "`datas`, `tasks`, `users`, `actions`, `commands`, `logs`, `devices`");

            // Action
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `actions` (" +
                            "  `id` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `id_actuator` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `value` varchar(20) NOT NULL," +
                            "  PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            // Command
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `commands` (" +
                            "  `id` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `category` varchar(30) COLLATE utf8_bin NOT NULL," +
                            "  `lock` int(11) NOT NULL," +
                            "  `time_out` int(11) NOT NULL," +
                            "  PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `commands_actions` (" +
                            "  `id_command` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `id_action` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  PRIMARY KEY (`id_command`,`id_action`)," +
                            "  KEY `id_action` (`id_action`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            // Device (sensor & actuator)
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `devices` (" +
                            "  `id` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `data_type` varchar(50) COLLATE utf8_bin NOT NULL," +
                            "  `location` varchar(100) COLLATE utf8_bin NOT NULL," +
                            "  `device_type` varchar(100) COLLATE utf8_bin NOT NULL," +
                            "  PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            // Data
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `datas` (" +
                            "  `id` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `id_device` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `value` varchar(20) NOT NULL," +
                            "  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'," +
                            "  PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            // Log
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `logs` (" +
                            "  `id` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `date` datetime NOT NULL," +
                            "  `from` varchar(200) COLLATE utf8_bin NOT NULL," +
                            "  `message` text COLLATE utf8_bin NOT NULL," +
                            "  `log_level` varchar(20) COLLATE utf8_bin NOT NULL," +
                            "  PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            // Task
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `tasks` (" +
                            "  `id` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `description` text COLLATE utf8_bin NOT NULL," +
                            "  `create_date` datetime NOT NULL," +
                            "  `expire_date` datetime NOT NULL," +
                            "  `taskstate` varchar(20) COLLATE utf8_bin NOT NULL," +
                            "  `script` text COLLATE utf8_bin NOT NULL," +
                            "  PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            // User
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `users` (" +
                            "  `id` varchar(20) COLLATE utf8_bin NOT NULL," +
                            "  `pwd` varchar(32) COLLATE utf8_bin NOT NULL," +
                            "  PRIMARY KEY (`id`)\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");


            /*
             * Constraints
             */

            // Command <-> Action
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("ALTER TABLE `commands_actions`" +
                            "  ADD CONSTRAINT `commands_actions_ibfk_1` FOREIGN KEY (`id_command`) REFERENCES `commands` (`id`) ON DELETE CASCADE," +
                            "  ADD CONSTRAINT `commands_actions_ibfk_2` FOREIGN KEY (`id_action`) REFERENCES `actions` (`id`) ON DELETE CASCADE");

            // Data -> Device
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("ALTER TABLE `datas`" +
                            "  ADD CONSTRAINT `datas_ibfk_1` FOREIGN KEY (`id_device`) REFERENCES `devices` (`id`) ON DELETE CASCADE");

        } catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }

    @Override
    public Database exportDb() {
        Database database = new Database();

        LinkedList<Device> deviceList = new LinkedList<Device>();
        try {
            ResultSet result = _daoFactory.getConnectionDb().getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM devices");
            if(result.first()) {
                if (result.getString("device_type").compareTo("esir.dom11.nsoc.model.device.Actuator")==0) {
                    deviceList.add(new Actuator(UUID.fromString(result.getString("id")),
                                        DataType.valueOf(result.getString("data_type")),
                                        result.getString("location")));
                } else if (result.getString("device_type").compareTo("esir.dom11.nsoc.model.device.Sensor")==0) {
                    deviceList.add(new Sensor(UUID.fromString(result.getString("id")),
                                        DataType.valueOf(result.getString("data_type")),
                                        result.getString("location")));
                }
            }
        } catch (SQLException exception) {
            logger.error("Device retrieve error", exception);
        }
        database.setDeviceList(deviceList);
        
        LinkedList<Data> dataList = new LinkedList<Data>();
        try {
            ResultSet result = _daoFactory.getConnectionDb().getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM datas");
            if(result.first()) {
                dataList.add(new Data(
                        UUID.fromString(result.getString("id")),
                        (Sensor)_daoFactory.getDeviceDAO().retrieve(UUID.fromString(result.getString("id_device"))),
                        result.getString("value"),
                        result.getDate("date")));
            }
        } catch (SQLException exception) {
            logger.error("Data retrieve error", exception);
        }
        database.setDataList(dataList);

        LinkedList<Command> commandList = new LinkedList<Command>();
        try {
            ResultSet result = _daoFactory.getConnectionDb().getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT id_command, id_action, category, lock, time_out, id_actuator, value " +
                            "FROM commands c " +
                            "JOIN commands_actions ca ON c.id=ca.id_command " +
                            "JOIN actions a ON ca.id_action=a.id");
            result.beforeFirst();
            LinkedList<Action> actionList = new LinkedList<Action>();
            while (result.next()) {
                actionList.add(_daoFactory.getActionDAO().retrieve(UUID.fromString(result.getString("id_actuator"))));
            }

            if(result.first()) {
                commandList.add(new Command(UUID.fromString(result.getString("id")),actionList, Category.valueOf(result.getString("category")),
                        result.getLong("category"),
                        result.getLong("time_out")));
            }
        } catch (SQLException exception) {
            logger.error("Command retrieve error", exception);
        }
        database.setCommandList(commandList);
        
        LinkedList<Log> logList = new LinkedList<Log>();
        try {
            ResultSet result = _daoFactory.getConnectionDb().getConnection()
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)
                    .executeQuery("SELECT * FROM logs");
            if(result.first()) {
                logList.add(new Log(UUID.fromString(result.getString("id")),
                                    result.getDate("date"),
                                    result.getString("from"),
                                    result.getString("message"),
                                    LogLevel.valueOf(result.getString("log_level"))));
            }
        } catch (SQLException exception) {
            logger.error("Log retrieve error", exception);
        }
        database.setLogList(logList);

        return database;
    }
}
