package esir.dom11.nsoc.context;

import esir.dom11.nsoc.context.energy.EnergyBalance;
import esir.dom11.nsoc.service.IDbService;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * This class analyze raw data and generate new indicators
 */
@Library(name = "NSOC_2011")
@ComponentType
@Provides({
        @ProvidedPort(name = "data", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "tempSetting", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "energyBalance", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "dbservice", type = PortType.SERVICE, className = IDbService.class, optional = true)
})
public class ContextAnalyzerComponent extends AbstractComponentType {

    private static Logger logger = LoggerFactory.getLogger(ContextAnalyzerComponent.class);
    private EnergyBalance _energyBalance;


    @Start
    public void start() {
        logger.info("= = = = = start context analyzer = = = = = =");

        _energyBalance = new EnergyBalance(0.0, 0.0);

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
    public void receiveData(Object obj) {

//        Data data = (Data) obj;
//        String dType = data.getDataType().getValue();
//
//        if (dType.compareTo("TEMPERATURE") == 0) {
//
//
//        } else if (dType.compareTo("POWER") == 0) {
//            if (data.getLocation().compareTo("energyconsumption") == 0) {
//                //    TODO
//                //               _energyBalance.setConsumption(data.getValue());
//                energyBalanceProduced(_energyBalance.getEnergyBalance());
//            } else if (data.getLocation().compareTo("energyproduction") == 0) {
//                // TODO
//                //      _energyBalance.setProduction(data.getValue());
//                energyBalanceProduced(_energyBalance.getEnergyBalance());
//            }
//        }

    }

    public void energyBalanceProduced(Double energyBalance) {
//        MessagePort energyBalancePort = getPortByName("energyBalance", MessagePort.class);
//        if (energyBalancePort != null) {
//            Data energyData = new Data(DataType.POWER, "energybalance", energyBalance, new Date());
//            energyBalancePort.process(energyData);
//        }
    }

    // TODO

}