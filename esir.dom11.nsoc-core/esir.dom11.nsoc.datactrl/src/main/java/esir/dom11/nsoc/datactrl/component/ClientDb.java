package esir.dom11.nsoc.datactrl.component;

// Logger
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.Log;
import esir.dom11.nsoc.model.LogLevel;
import esir.dom11.nsoc.model.device.Device;
import esir.dom11.nsoc.model.device.Sensor;
import esir.dom11.nsoc.service.RequestResult;
import org.kevoree.framework.MessagePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import esir.dom11.nsoc.service.IDbService;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

import java.util.Date;
import java.util.LinkedList;

@Requires({
    @RequiredPort(name = "dbService", type = PortType.SERVICE, className = IDbService.class, needCheckDependency = true),
    @RequiredPort(name = "log", type = PortType.MESSAGE, optional = true)
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

        Sensor sensor = new Sensor(DataType.TEMPERATURE,"temp-int-salle930");
        System.out.println("New device: "+sensor);
        // Save device
        Device sensorSave = (Device)dbService.create(sensor);
        System.out.println("Saved device: "+sensorSave);
        
        // Data samples
        Data data1 = new Data(sensor,"19.6", new Date(new Long("1326098200720")));
        Data data2 = new Data(sensor,"19.3", new Date(new Long("1326098202743")));
        Data data3 = new Data(sensor,"19.5", new Date(new Long("1326098204754")));
        Data data4 = new Data(sensor,"20.3", new Date(new Long("1326098206765")));
        Data data5 = new Data(sensor,"19.8", new Date(new Long("1326098208787")));

        logger.info("*** *** Save data 1,2,3,4,5 *** ***");

        // Save data
        RequestResult result1 = dbService.create(data1);
        RequestResult result2 = dbService.create(data2);
        RequestResult result3 = dbService.create(data3);
        RequestResult result4 = dbService.create(data4);
        RequestResult result5 = dbService.create(data5);

        if (result1.isSuccess()) {
            Data data1Save = (Data)dbService.create(data1).getResult();
        }

        if (result2.isSuccess()) {
            Data data2Save = (Data)dbService.create(data2).getResult();
        }

        if (result3.isSuccess()) {
            Data data3Save = (Data)dbService.create(data3).getResult();
        }

        if (result4.isSuccess()) {
            Data data4Save = (Data)dbService.create(data4).getResult();
        }

        if (result5.isSuccess()) {
            Data data5Save = (Data)dbService.create(data5).getResult();
        }

        // Retrieve data 1 (by id)
        RequestResult retrieveResult = dbService.retrieve(Data.class.getName(),data1.getId());
        if (retrieveResult.isSuccess()) {
            Data data1Retrieve = (Data)retrieveResult.getResult();
            logger.info("*** *** Retrieve data 1 *** ***");
            logger.info(data1Retrieve.toString());
        }

        // find by date (use get())
        logger.info("*** *** Find By Date *** ***");

        LinkedList<Object> params = new LinkedList<Object>();

        params.add(new Date(new Long("1326098201732")));
        params.add(new Date(new Long("1326098207775")));
        params.add("temp-int-salle930");
        params.add(DataType.TEMPERATURE);

        logger.info(" - Start date: "+ new Date(new Long("1326098201732")));
        logger.info(" - End date: "+ new Date(new Long("1326098207775")));
        logger.info(" - Location: "+ "temp-int-salle930");
        logger.info(" - DataType: "+ DataType.TEMPERATURE);

        RequestResult result = dbService.get("findByDate", Data.class.getName(), params);
        logger.info("Data retrieve:\n");
        if (result.isSuccess()) {
            for ( Data data : (LinkedList<Data>)result.getResult()) {
                logger.info(data.toString());
            }
        }

        // delete data
        RequestResult delete1Result = dbService.delete(Data.class.getName(),data1.getId());
        if (delete1Result.isSuccess()) {
            // ...
        }
        dbService.delete(Data.class.getName(),data2.getId());
        dbService.delete(Data.class.getName(),data3.getId());
        dbService.delete(Data.class.getName(),data4.getId());
        dbService.delete(Data.class.getName(),data5.getId());
        
        // send log
        Log log = new Log(ClientDb.class.getName(),"Test log", LogLevel.INFO);
        logger.info("send log");
        getPortByName("log",MessagePort.class).process(log);
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
