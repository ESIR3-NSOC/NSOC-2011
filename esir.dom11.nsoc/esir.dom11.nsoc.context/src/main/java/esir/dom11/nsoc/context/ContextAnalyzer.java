package esir.dom11.nsoc.context;


import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.service.IDbService;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *      This class analyze raw data and generate new indicators
 */
@Library(name = "NSOC_2011")
@ComponentType
@Provides({
        @ProvidedPort(name = "data", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "dbservice", type = PortType.SERVICE, className = IDbService.class, optional = true)
})
public class ContextAnalyzer extends AbstractComponentType {

    private static Logger logger = LoggerFactory.getLogger(ContextAnalyzer.class);

    @Start
    public void start() {
        logger.info("= = = = = start context analyzer = = = = = =");
    }

    @Stop
    public void stop() {
        logger.info("= = = = = stop context analyzer = = = = = =");
    }

    @Update
    public void update() {
        logger.info("= = = = = update context analyzer = = = = = =");
    }

    
    @Port(name = "data")
    public void receiveData(Data data){
        
        String dType = data.getDataType().getValue();
        
        if(dType.compareTo("TEMPERATURE")==0){
            // TODO test if temp ext / int / setting
        }

    }
    
    // TODO

}
