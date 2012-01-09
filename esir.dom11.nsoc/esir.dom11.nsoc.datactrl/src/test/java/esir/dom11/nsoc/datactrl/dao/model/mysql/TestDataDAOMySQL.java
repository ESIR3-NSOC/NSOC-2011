package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactory;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.UUID;

public class TestDataDAOMySQL extends TestCase {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(TestDataDAOMySQL.class.getName());
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
    }

    /*
     * Tests
     */

    public void testCRUDData() {
        Data data = new Data(DataType.TEMPERATURE,UUID.randomUUID(),19.6, new Date());
        logger.info("New Data:" + data.toString());

        Data createData = _daoFactory.getDataDAO().create(data);
        assertNotNull(createData);
        logger.info("Data Saved:" + createData.toString());

        Data retrieveData = _daoFactory.getDataDAO().retrieve(data.getId());
        assertNotNull(retrieveData);
        logger.info("Data Retrieve:" + retrieveData.toString());

        data.setValue(20.1);
        data.setDate(new Date());
        Data updateData = _daoFactory.getDataDAO().update(data);
        logger.info(updateData.toString());
        assertNotNull(retrieveData);
        assertEquals(updateData.getValue(),data.getValue());
        logger.info("Data Update:\n" + data.toString());

        assertTrue(_daoFactory.getDataDAO().delete(data.getId()));
        logger.info("Data Delete");
    }
    
    public void testFindByDate() {
        UUID idSensor = UUID.randomUUID();
        /*_daoFactory.getDataDAO().create(new Data(DataType.TEMPERATURE,idSensor,19.6, new Date(1326033456)));
        _daoFactory.getDataDAO().create(new Data(DataType.TEMPERATURE,idSensor,19.6, new Date(1326043456)));
        _daoFactory.getDataDAO().create(new Data(DataType.TEMPERATURE,idSensor,19.6, new Date(1326053456)));
        _daoFactory.getDataDAO().create(new Data(DataType.TEMPERATURE,idSensor,19.6, new Date(1326063456)));
        _daoFactory.getDataDAO().create(new Data(DataType.TEMPERATURE,idSensor,19.6, new Date(1326073456))); */
        System.out.println((new Date(1326040000)));
        LinkedList<Data> dataList = _daoFactory.getDataDAO().findByDate(new Date(1326040000),new Date(1326060656),idSensor);
        System.out.println(dataList.size());
        assertTrue(dataList.size()==1);
    }

    /*
    * Methods
    */

    public void initProperties() {
        _dbProperties = new Properties();
        FileReader fr = null;
        try {
            fr = new FileReader(getClass().getClassLoader().getResource("config.properties").getFile());
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
