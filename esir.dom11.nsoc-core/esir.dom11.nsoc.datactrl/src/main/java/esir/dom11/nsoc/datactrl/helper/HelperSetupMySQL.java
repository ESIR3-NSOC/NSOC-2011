package esir.dom11.nsoc.datactrl.helper;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryMySQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.sql.Statement;

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
            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("SET SQL_MODE=\"NO_AUTO_VALUE_ON_ZERO\"");

            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("SET time_zone = \"+00:00\"");

            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("DROP TABLE IF EXISTS `commands_actions`, " +
                            "`datas`, `tasks`, `users`, `actions`, `commands`, `logs`, `devices`");

            // Action
            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `actions` (" +
                            "  `id` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `id_actuator` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `value` double NOT NULL," +
                            "  PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            // Command
            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `commands` (" +
                            "  `id` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `category` varchar(30) COLLATE utf8_bin NOT NULL," +
                            "  `lock` int(11) NOT NULL," +
                            "  `time_out` int(11) NOT NULL," +
                            "  PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `commands_actions` (" +
                            "  `id_command` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `id_action` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  PRIMARY KEY (`id_command`,`id_action`)," +
                            "  KEY `id_action` (`id_action`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            // Device (sensor & actuator)
            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `devices` (" +
                            "  `id` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `data_type` varchar(50) COLLATE utf8_bin NOT NULL," +
                            "  `location` varchar(100) COLLATE utf8_bin NOT NULL," +
                            "  `device_type` varchar(100) COLLATE utf8_bin NOT NULL," +
                            "  PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            // Data
            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `datas` (" +
                            "  `id` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `id_device` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `value` double NOT NULL," +
                            "  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'," +
                            "  PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            // Log
            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `logs` (" +
                            "  `id` varchar(36) COLLATE utf8_bin NOT NULL," +
                            "  `date` datetime NOT NULL," +
                            "  `from` varchar(200) COLLATE utf8_bin NOT NULL," +
                            "  `message` text COLLATE utf8_bin NOT NULL," +
                            "  `log_level` varchar(20) COLLATE utf8_bin NOT NULL," +
                            "  PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");

            // Task
            _daofactory.getConnectionDb().getConnection().createStatement()
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
            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `users` (" +
                            "  `id` varchar(20) COLLATE utf8_bin NOT NULL," +
                            "  `pwd` varchar(32) COLLATE utf8_bin NOT NULL," +
                            "  PRIMARY KEY (`id`)\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin");


            /*
             * Constraints
             */

            // Command <-> Action
            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("ALTER TABLE `commands_actions`" +
                            "  ADD CONSTRAINT `commands_actions_ibfk_1` FOREIGN KEY (`id_command`) REFERENCES `commands` (`id`) ON DELETE CASCADE," +
                            "  ADD CONSTRAINT `commands_actions_ibfk_2` FOREIGN KEY (`id_action`) REFERENCES `actions` (`id`) ON DELETE CASCADE");

            // Data -> Device
            _daofactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("ALTER TABLE `datas`" +
                            "  ADD CONSTRAINT `datas_ibfk_1` FOREIGN KEY (`id_device`) REFERENCES `devices` (`id`) ON DELETE CASCADE");

        } catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

        /*String s;
        StringBuilder sb = new StringBuilder();

        try {
            System.out.println(this.getClass().getClassLoader().getResource("db/dbMySQL.sql").getFile());
            FileReader fr = new FileReader(this.getClass().getClassLoader().getResource("db/dbMySQL.sql").getFile());
            BufferedReader br = new BufferedReader(fr);

            while((s = br.readLine()) != null) {
                if (!s.matches("--.*")) {
                    sb.append(s);
                }
            }
            br.close();

            // here is our splitter ! We use ";" as a delimiter for each request
            // then we are sure to have well formed statements
            String[] inst = sb.toString().split(";");

            Statement statement = _daofactory.getConnectionDb().getConnection().createStatement();

            for(String query : inst) {
                // we ensure that there is no spaces before or after the request string
                // in order to not execute empty statements
                if(!query.trim().equals("")) {
                    statement.executeUpdate(query);
                    logger.debug(">>" + query);
                }
            }
        } catch (Exception exception) {
            logger.error("Error Setup Dd", exception);
        }*/
    }

    public void setupData() {

    }

    @Override
    public Database exportDb() {
        return null;//To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void importDb(Database database) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
