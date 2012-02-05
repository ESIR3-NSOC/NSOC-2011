package esir.dom11.nsoc.datactrl.helper;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryDb4o;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelperSetupDb4o extends HelperSetup {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(HelperSetupDb4o.class.getName());

    /*
     * Constructors
     */

    public HelperSetupDb4o(DAOFactoryDb4o daoFactoryDb4o) {
        super(daoFactoryDb4o);
    }

    /*
     * Overrides
     */

    @Override
    public void setupTable() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Database exportDb() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
