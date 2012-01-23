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
        _daoFactory.getHelperSetup().setupData();
    }

    /*
     * Tests
     */

    public void testCRUDData() {
        /*Data data = new Data(DataType.TEMPERATURE,"temp-int-salle930",19.6, new Date());
        logger.info("New Data:" + data.toString());
        System.out.println("New Data:" + data.toString());

        Data createData = _daoFactory.getDataDAO().create(data);
        assertNotNull(createData);
        logger.info("Data Saved:" + createData.toString());
        System.out.println("Data Saved:" + createData.toString());

        Data retrieveData = _daoFactory.getDataDAO().retrieve(data.getId());
        assertNotNull(retrieveData);
        logger.info("Data Retrieve:" + retrieveData.toString());
        System.out.println("Data Retrieve:" + retrieveData.toString());

        assertTrue(_daoFactory.getDataDAO().delete(data.getId()));
        logger.info("Data Delete");
        System.out.println("Data Delete");*/
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
