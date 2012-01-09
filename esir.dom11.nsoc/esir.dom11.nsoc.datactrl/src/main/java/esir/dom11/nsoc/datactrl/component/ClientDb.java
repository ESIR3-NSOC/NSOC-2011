package esir.dom11.nsoc.datactrl.component;

// Logger
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.Task;
import esir.dom11.nsoc.model.TaskState;
import esir.dom11.nsoc.service.RequestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import esir.dom11.nsoc.service.IDbService;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

import java.util.Date;
import java.util.LinkedList;

@Requires({
        @RequiredPort(name = "dbService", type = PortType.SERVICE, className = IDbService.class, needCheckDependency = true)
})
@Library(name = "NSOC_2011")
@ComponentType
public class ClientDb extends AbstractComponentType {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ClientDb.class.getName());

    /*
     * Overrides
     */

    @Start
    public void start() {
        logger.info("**** clientdb start ****");
        
        logger.info("*** complete save data sample ***");
        // retrieve dbService
        IDbService dbService = getPortByName("dbService", IDbService.class);
        
        // Data samples
        Data data1 = new Data(DataType.TEMPERATURE,"temp-int-salle930",19.6, new Date(new Long("1326098200720")));
        Data data2 = new Data(DataType.TEMPERATURE,"temp-int-salle930",19.3, new Date(new Long("1326098202743")));
        Data data3 = new Data(DataType.TEMPERATURE,"temp-int-salle930",19.5, new Date(new Long("1326098204754")));
        Data data4 = new Data(DataType.TEMPERATURE,"temp-int-salle930",20.3, new Date(new Long("1326098206765")));
        Data data5 = new Data(DataType.TEMPERATURE,"temp-int-salle930",19.8, new Date(new Long("1326098208787")));

        logger.info("*** *** Save data 1,2,3,4,5 *** ***");

        // Save data
        Data data1Save = (Data)dbService.create(data1);
        Data data2Save = (Data)dbService.create(data2);
        Data data3Save = (Data)dbService.create(data3);
        Data data4Save = (Data)dbService.create(data4);
        Data data5Save = (Data)dbService.create(data5);

        // Retrieve data 1 (by id)
        Data data1Retrieve = (Data)dbService.retrieve(Data.class.getName(),data1.getId());
        logger.info("*** *** Retrieve data 1 *** ***");
        logger.info(data1Retrieve.toString());

        // find by date (use get())
        logger.info("*** *** Find By Date *** ***");

        LinkedList<Object> params = new LinkedList<Object>();

        params.add(new Date(new Long("1326098201732")));
        params.add(new Date(new Long("1326098207775")));
        params.add("temp-int-salle930");

        logger.info(" - Start date: "+ new Date(new Long("1326098201732")));
        logger.info(" - End date: "+ new Date(new Long("1326098207775")));
        logger.info(" - Role: "+ "temp-int-salle930");

        RequestResult result = getPortByName("dbService", IDbService.class).get("findByDate", Data.class.getName(), params);
        logger.info("Data retrieve:\n");
        if (result.isSuccess()) {
            for ( Data data : (LinkedList<Data>)result.getResult()) {
                logger.info(data.toString());
            }
        }

        // delete data
        dbService.delete(Data.class.getName(),data1.getId());
        dbService.delete(Data.class.getName(),data2.getId());
        dbService.delete(Data.class.getName(),data3.getId());
        dbService.delete(Data.class.getName(),data4.getId());
        dbService.delete(Data.class.getName(),data5.getId());
    }

    @Stop
    public void stop() {
        logger.warn("**** clientdb stop ****");
    }

    @Update
    public void update() {
        logger.warn("**** clientdb update ****");
    }
}
