package esir.dom11.nsoc.datactrl.helper;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactory;
import esir.dom11.nsoc.datactrl.dao.factory.DAOFactorySQLite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class HelperSetupSQLite extends HelperSetup {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(HelperSetupSQLite.class.getName());

    /*
     * Constructors
     */

    public HelperSetupSQLite(DAOFactorySQLite daoFactorySQLite) {
        super(daoFactorySQLite);
    }

    /*
     * Overrides
     */

    @Override
    public void setupTable() {
        try {
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("DROP TABLE IF EXISTS `commands_actions`");
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("DROP TABLE IF EXISTS `datas`");
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("DROP TABLE IF EXISTS `tasks`");
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("DROP TABLE IF EXISTS `users`");
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("DROP TABLE IF EXISTS `actions`");
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("DROP TABLE IF EXISTS `commands`");
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("DROP TABLE IF EXISTS `logs`");
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("DROP TABLE IF EXISTS `devices`");

            // Action
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `actions` (" +
                            "  `id` VARCHAR(36) PRIMARY KEY," +
                            "  `id_actuator` VARCHAR(36) NOT NULL," +
                            "  `value` DOUBLE NOT NULL)");

            // Command
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `commands` (" +
                            "  `id` VARCHAR(36) PRIMARY KEY," +
                            "  `category` VARCHAR(30) NOT NULL," +
                            "  `lock` int(11) NOT NULL," +
                            "  `time_out` int(11) NOT NULL)");

            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `commands_actions` (" +
                            "  `id_command` VARCHAR(36) NOT NULL," +
                            "  `id_action` VARCHAR(36) NOT NULL," +
                            " PRIMARY KEY (`id_command`,`id_action`)," +
                            " FOREIGN KEY (`id_command`) REFERENCES `commands` (`id`) ON DELETE CASCADE," +
                            " FOREIGN KEY (`id_action`) REFERENCES `actions` (`id`) ON DELETE CASCADE)");

            // Device (sensor & actuator)
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `devices` (" +
                            "  `id` VARCHAR(36) PRIMARY KEY," +
                            "  `data_type` VARCHAR(50) NOT NULL," +
                            "  `location` VARCHAR(100) NOT NULL," +
                            "  `device_type` VARCHAR(100) NOT NULL)");

            // Data
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `datas` (" +
                            "  `id` VARCHAR(36) PRIMARY KEY," +
                            "  `id_device` VARCHAR(36) NOT NULL," +
                            "  `value` DOUBLE NOT NULL," +
                            "  `date` TEXT NOT NULL," +
                            "   FOREIGN KEY (`id_device`) REFERENCES `devices` (`id`) ON DELETE CASCADE)");

            // Log
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `logs` (" +
                            "  `id` VARCHAR(36) PRIMARY KEY," +
                            "  `date` TEXT NOT NULL," +
                            "  `from` VARCHAR(200) NOT NULL," +
                            "  `message` TEXT NOT NULL," +
                            "  `log_level` VARCHAR(20) NOT NULL)");

            // Task
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `tasks` (" +
                            "  `id` VARCHAR(36) PRIMARY KEY," +
                            "  `description` TEXT NOT NULL," +
                            "  `create_date` TEXT NOT NULL," +
                            "  `expire_date` TEXT NOT NULL," +
                            "  `taskstate` VARCHAR(20) NOT NULL," +
                            "  `script` TEXT NOT NULL)");

            // User
            _daoFactory.getConnectionDb().getConnection().createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS `users` (" +
                            "  `id` VARCHAR(20) PRIMARY KEY," +
                            "  `pwd` VARCHAR(32) NOT NULL)");

        } catch(SQLException ex) {
            System.out.println("HelperSetupSQLite.SetupTable:\nSQLException: " + ex.getMessage());
        }
    }

    @Override
    public Database exportDb() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
