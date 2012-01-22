package esir.dom11.nsoc.datactrl.helper;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryMongoDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelperSetupMongoDb extends HelperSetup {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(HelperSetupMongoDb.class.getName());

    /*
     * Constructors
     */

    public HelperSetupMongoDb(DAOFactoryMongoDb daoFactoryMongoDb) {
        super(daoFactoryMongoDb);
    }

    /*
     * Overrides
     */

    @Override
    public void setupTable() {

    }

    @Override
    public void setupData() {

    }
}
