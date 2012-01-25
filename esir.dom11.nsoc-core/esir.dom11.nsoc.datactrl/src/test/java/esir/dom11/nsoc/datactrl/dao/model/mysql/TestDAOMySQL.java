package esir.dom11.nsoc.datactrl.dao.model.mysql;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactory;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.Log;
import esir.dom11.nsoc.model.LogLevel;
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
        Data data = new Data(DataType.TEMPERATURE,"temp-int-salle930",19.6, new Date());
        logger.info("New Data:" + data.toString());

        Data createData = _daoFactory.getDataDAO().create(data);
        assertNotNull(createData);
        logger.info("Data Saved:" + createData.toString());

        Data retrieveData = _daoFactory.getDataDAO().retrieve(data.getId());
        assertNotNull(retrieveData);
        logger.info("Data Retrieve:" + retrieveData.toString());

        assertTrue(_daoFactory.getDataDAO().delete(data.getId()));
        logger.info("Data Delete");
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
    
    public void testFindByDate() {
        Data data1 = new Data(DataType.TEMPERATURE,"temp-int-salle930",19.6, new Date(new Long("1326098200720")));
        Data data2 = new Data(DataType.TEMPERATURE,"temp-int-salle930",19.6, new Date(new Long("1326098202743")));
        Data data3 = new Data(DataType.TEMPERATURE,"temp-int-salle930",19.6, new Date(new Long("1326098204754")));
        Data data4 = new Data(DataType.TEMPERATURE,"temp-int-salle930",19.6, new Date(new Long("1326098206765")));
        Data data5 = new Data(DataType.TEMPERATURE,"temp-int-salle930",19.6, new Date(new Long("1326098208787")));

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
