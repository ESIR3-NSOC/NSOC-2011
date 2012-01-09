package esir.dom11.nsoc.datactrl.helper;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HelperSetup {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(HelperSetup.class.getName());

    /*
     * Attributes
     */

    protected DAOFactory _daofactory;

    /*
     * Constructors
     */

    public HelperSetup(DAOFactory daoFactory) {
        _daofactory = daoFactory;
    }

    /*
     * Methods
     */

    public abstract void setupTable();

    public abstract void setupData();
}
