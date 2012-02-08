package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactory;
import esir.dom11.nsoc.model.*;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Device;
import esir.dom11.nsoc.model.device.Sensor;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

public class TestDAOMySQL extends TestCase {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(TestDAOMySQL.class.getName());
    /*
     * Attributes
     */

    private Properties _dbProperties;
    private DAOFactory _daoFactory;

    /*
     * Overrides
     */

    @Override
    public void setUp() throws Exception {
        super.setUp();
        initProperties();
        _daoFactory = DAOFactory.getFactory(_dbProperties);
        _daoFactory.getHelperSetup().setupTable();
        _daoFactory.getHelperSetup().setupData();
    }

    /*
     * Tests
     */

    public void testCRUDData() {

        Sensor sensor = new Sensor(DataType.TEMPERATURE,"bat7/930");
        // Save device
        Sensor sensorSave = (Sensor)_daoFactory.getDeviceDAO().create(sensor);

        Data data = new Data(sensor,"19.6", new Date());
        logger.info("New Data:" + data.toString());

        Data createData = _daoFactory.getDataDAO().create(data);
        assertNotNull(createData);

        Data retrieveData = _daoFactory.getDataDAO().retrieve(data.getId());
        assertNotNull(retrieveData);
        logger.info("Data Retrieve:" + retrieveData.toString());

        assertTrue(_daoFactory.getDataDAO().delete(data.getId()));
        logger.info("Data Delete");

        _daoFactory.getDeviceDAO().delete(sensor.getId());
    }

    public void testCRUDLog() {
        Log log = new Log( TestDAOMySQL.class.getName(), "Un log de test", LogLevel.INFO);
        logger.info("New Log:" + log.toString());

        Log createLog = _daoFactory.getLogDAO().create(log);
        assertNotNull(createLog);
        logger.info("Log Saved:" + createLog.toString());

        Log retrieveLog = _daoFactory.getLogDAO().retrieve(log.getId());
        assertNotNull(retrieveLog);
        logger.info("Log Retrieve:" + retrieveLog.toString());

        assertTrue(_daoFactory.getLogDAO().delete(log.getId()));
        logger.info("Log Delete");
    }

    public void testCRUDCommand() {
        Actuator actuator = new Actuator(DataType.TEMPERATURE,"bat7/930");

        LinkedList<Action> actionList = new LinkedList<Action>();
        logger.info("New Actuator:" + actuator.toString());
        Action act1 = new Action(actuator,"1");
        actionList.add(act1);
        logger.info("New Action:" + act1.toString());
        Action act2 = new Action(actuator,"2");
        actionList.add(act2);
        logger.info("New Action:" + act2.toString());
        Action act3 = new Action(actuator,"3");
        actionList.add(act3);
        logger.info("New Action:" + act3.toString());
        Action act4 = new Action(actuator,"4");
        actionList.add(act4);
        logger.info("New Action:" + act4.toString());
        Action act5 = new Action(actuator,"5");
        actionList.add(act5);
        logger.info("New Action:" + act5.toString());

        Command cmd = new Command(actionList,Category.AUTO,0,0);

        Command createCommand = _daoFactory.getCommandDAO().create(cmd);
        assertEquals(createCommand.getId().toString(),"00000000-0000-0000-0000-000000000000");
        logger.info("Command Saved:" + createCommand.toString());
        System.out.println("Command Saved:" + createCommand.toString());

        Command retrieveCommand = _daoFactory.getCommandDAO().retrieve(cmd.getId());
        assertNotNull(retrieveCommand);
        logger.info("Command Retrieve:" + retrieveCommand.toString());

        assertTrue(_daoFactory.getCommandDAO().delete(cmd.getId()));
        logger.info("Command Delete");
    }
    
    public void testFindByDate() {

        Sensor sensor = new Sensor(DataType.TEMPERATURE,"bat7/930");
        System.out.println("New device: "+sensor);
        // Save device
        Device deviceSave = _daoFactory.getDeviceDAO().create(sensor);
        System.out.println("Saved device: "+deviceSave);

        Data data1 = new Data(sensor,"19.6", new Date(new Long("1326098200720")));
        Data data2 = new Data(sensor,"19.6", new Date(new Long("1326098202743")));
        Data data3 = new Data(sensor,"19.6", new Date(new Long("1326098204754")));
        Data data4 = new Data(sensor,"19.6", new Date(new Long("1326098206765")));
        Data data5 = new Data(sensor,"19.6", new Date(new Long("1326098208787")));

        System.out.println(_daoFactory.getDataDAO().create(data1));
        _daoFactory.getDataDAO().create(data2);
        _daoFactory.getDataDAO().create(data3);
        _daoFactory.getDataDAO().create(data4);
        _daoFactory.getDataDAO().create(data5);

        LinkedList<Data> dataList = _daoFactory.getDataDAO().findByDate(
                                    new Date(new Long("1326098201732")),
                                    new Date(new Long("1326098207775")),
                                    "temp-int-salle930",
                                    DataType.TEMPERATURE);
        System.out.println(dataList.size());
        assertTrue(dataList.size()==3);

        _daoFactory.getDataDAO().delete(data1.getId());
        _daoFactory.getDataDAO().delete(data2.getId());
        _daoFactory.getDataDAO().delete(data3.getId());
        _daoFactory.getDataDAO().delete(data4.getId());
        _daoFactory.getDataDAO().delete(data5.getId());

        _daoFactory.getDeviceDAO().delete(sensor.getId());
    }

    /*
    * Methods
    */

    public void initProperties() {
        _dbProperties = new Properties();
        FileReader fr = null;
        try {
            fr = new FileReader(getClass().getClassLoader().getResource("configMySQL.properties").getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            _dbProperties.load(fr);
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
