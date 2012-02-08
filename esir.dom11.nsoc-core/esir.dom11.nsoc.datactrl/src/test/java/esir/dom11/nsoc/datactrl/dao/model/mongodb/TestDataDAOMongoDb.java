package esir.dom11.nsoc.datactrl.dao.model.mongodb;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactory;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.device.Device;
import esir.dom11.nsoc.model.device.Sensor;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class TestDataDAOMongoDb extends TestCase {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(TestDataDAOMongoDb.class.getName());

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
        //_daoFactory.getHelperSetup().setupData();
    }

    /*
     * Tests
     */

    public void testCRUDData() {

        Sensor sensor = new Sensor(DataType.TEMPERATURE,"bat7/930");
        System.out.println("New device: "+sensor);
        // Save device
        Sensor sensorSave = (Sensor)_daoFactory.getDeviceDAO().create(sensor);
        System.out.println("Saved device: "+sensorSave);

        Data data = new Data(sensor,"19.6", new Date());
        logger.info("New Data:" + data.toString());

        Data createData = _daoFactory.getDataDAO().create(data);
        assertNotNull(createData);
        logger.info("Data Saved:" + createData.toString());

        Data retrieveData = _daoFactory.getDataDAO().retrieve(data.getId());
        assertNotNull(retrieveData);
        logger.info("Data Retrieve:" + retrieveData.toString());

        assertTrue(_daoFactory.getDataDAO().delete(data.getId()));
        logger.info("Data Delete");

        _daoFactory.getDeviceDAO().delete(sensor.getId());
    }

    /*
    * Methods
    */

    public void initProperties() {
        _dbProperties = new Properties();
        FileReader fr = null;
        try {
            fr = new FileReader(getClass().getClassLoader().getResource("configMongoDb.properties").getFile());
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
