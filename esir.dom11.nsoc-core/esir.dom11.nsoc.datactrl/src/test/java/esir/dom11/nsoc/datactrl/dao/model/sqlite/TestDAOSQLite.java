package esir.dom11.nsoc.datactrl.dao.model.sqlite;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactory;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.Log;
import esir.dom11.nsoc.model.LogLevel;
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

public class TestDAOSQLite extends TestCase {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(TestDAOSQLite.class.getName());
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

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        _daoFactory.getConnectionDb().disconnect();
    }

    /*
     * Tests
     */

    public void testCRUDData() {

        Device device = new Sensor(DataType.TEMPERATURE,"temp-int-salle930");
        System.out.println("New device: "+device);
        // Save device
        Device deviceSave = _daoFactory.getDeviceDAO().create(device);
        System.out.println("Saved device: "+deviceSave);

        Data data = new Data(device,19.6, new Date());
        logger.info("New Data:" + data.toString());
        System.out.println("New data: " + data);

        Data createData = _daoFactory.getDataDAO().create(data);
        assertNotNull(createData.getId().toString().compareTo("00000000-0000-0000-0000-000000000000")!=0);
        logger.info("Data Saved:" + createData.toString());
        System.out.println("Data Saved: " + createData.toString());

        Data retrieveData = _daoFactory.getDataDAO().retrieve(data.getId());
        assertNotNull(retrieveData);
        logger.info("Data Retrieve:" + retrieveData.toString());
        System.out.println("Data Retrieve:" + retrieveData.toString());

        assertTrue(_daoFactory.getDataDAO().delete(data.getId()));
        logger.info("Data Delete");

        _daoFactory.getDeviceDAO().delete(device.getId());
    }

    public void testCRUDLog() {
        Log log = new Log( TestDAOSQLite.class.getName(), "Un log de test", LogLevel.INFO);
        logger.info("New Log:" + log.toString());
        System.out.println("New log: " + log);

        Log createLog = _daoFactory.getLogDAO().create(log);
        assertNotNull(createLog);
        logger.info("Log Saved:" + createLog.toString());
        System.out.println("Log Saved: " + createLog);

        Log retrieveLog = _daoFactory.getLogDAO().retrieve(log.getId());
        assertNotNull(retrieveLog);
        logger.info("Log Retrieve:" + retrieveLog.toString());
        System.out.println("Log Retrieve: " + retrieveLog);


        assertTrue(_daoFactory.getLogDAO().delete(log.getId()));
        logger.info("Log Delete");
    }

    public void testFindByDate() {

        Device device = new Sensor(DataType.TEMPERATURE,"temp-int-salle930");
        System.out.println("New device: "+device);
        // Save device
        Device deviceSave = _daoFactory.getDeviceDAO().create(device);
        System.out.println("Saved device: "+deviceSave);

        Data data1 = new Data(device,19.6, new Date(new Long("1326098200720")));
        Data data2 = new Data(device,19.6, new Date(new Long("1326098202743")));
        Data data3 = new Data(device,19.6, new Date(new Long("1326098204754")));
        Data data4 = new Data(device,19.6, new Date(new Long("1326098206765")));
        Data data5 = new Data(device,19.6, new Date(new Long("1326098208787")));

        System.out.println(_daoFactory.getDataDAO().create(data1));
        _daoFactory.getDataDAO().create(data2);
        _daoFactory.getDataDAO().create(data3);
        _daoFactory.getDataDAO().create(data4);
        _daoFactory.getDataDAO().create(data5);

        LinkedList<Data> dataList = _daoFactory.getDataDAO().findByDate(new Date(new Long("1326098201732")),new Date(new Long("1326098207775")),"temp-int-salle930");
        System.out.println(dataList.size());
        assertTrue(dataList.size()==3);

        _daoFactory.getDataDAO().delete(data1.getId());
        _daoFactory.getDataDAO().delete(data2.getId());
        _daoFactory.getDataDAO().delete(data3.getId());
        _daoFactory.getDataDAO().delete(data4.getId());
        _daoFactory.getDataDAO().delete(data5.getId());

        _daoFactory.getDeviceDAO().delete(device.getId());
    }

    /*
    * Methods
    */

    public void initProperties() {
        _dbProperties = new Properties();
        FileReader fr = null;
        try {
            fr = new FileReader(getClass().getClassLoader().getResource("configSQLite.properties").getFile());
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
