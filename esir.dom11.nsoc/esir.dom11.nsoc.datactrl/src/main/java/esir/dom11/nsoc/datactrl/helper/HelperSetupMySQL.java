package esir.dom11.nsoc.datactrl.helper;

import esir.dom11.nsoc.datactrl.dao.factory.DAOFactoryMySQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Statement;

public class HelperSetupMySQL extends HelperSetup {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(HelperSetupMySQL.class.getName());

    /*
     * Constructors
     */

    public HelperSetupMySQL(DAOFactoryMySQL daoFactoryMySQL) {
        super(daoFactoryMySQL);
    }

    /*
     * Overrides
     */

    public void setupTable() {
        String s;
        StringBuilder sb = new StringBuilder();

        try {
            FileReader fr = new FileReader(new File("dbMySQL.sql"));
            BufferedReader br = new BufferedReader(fr);

            while((s = br.readLine()) != null) {
                if (!s.matches("--.*")) {
                    sb.append(s);
                }
            }
            br.close();

            // here is our splitter ! We use ";" as a delimiter for each request
            // then we are sure to have well formed statements
            String[] inst = sb.toString().split(";");

            Statement statement = _daofactory.getConnectionDb().getConnection().createStatement();

            for(String query : inst) {
                // we ensure that there is no spaces before or after the request string
                // in order to not execute empty statements
                if(!query.trim().equals("")) {
                    statement.executeUpdate(query);
                    logger.debug(">>" + query);
                }
            }
        } catch (Exception exception) {
            logger.error("Error Setup Dd", exception);
        }
    }

    public void setupData() {

    }

}
