package esir.dom11.nsoc.context;


import esir.dom11.nsoc.context.energybalance.EnergyBalance;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.service.IDbService;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

/**
 * This class analyze raw data and generate new indicators
 */
@Library(name = "NSOC_2011")
@ComponentType
@Provides({
        @ProvidedPort(name = "data", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "tempSetting", type = PortType.MESSAGE),
        @RequiredPort(name = "brightnessSetting", type = PortType.MESSAGE),
        @RequiredPort(name = "energyBalance", type = PortType.MESSAGE),
        @RequiredPort(name = "dbservice", type = PortType.SERVICE, className = IDbService.class, optional = true)
})
public class ContextAnalyzer extends AbstractComponentType {

    private static Logger logger = LoggerFactory.getLogger(ContextAnalyzer.class);
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
    public void receiveData(Data data) {

        String dType = data.getDataType().getValue();

        if (dType.compareTo("TEMPERATURE") == 0) {


        } else if (dType.compareTo("POWER") == 0) {
            if (data.getRole().compareTo("energyconsumption") == 0) {
                _energyBalance.setConsumption(data.getValue());
                energyBalanceProduced(_energyBalance.getEnergyBalance());
            } else if (data.getRole().compareTo("energyproduction") == 0) {
                _energyBalance.setProduction(data.getValue());
                energyBalanceProduced(_energyBalance.getEnergyBalance());
            }
        }

    }

    public void energyBalanceProduced(Double energyBalance) {
        MessagePort energyBalancePort = getPortByName("energyBalance", MessagePort.class);
        if (energyBalancePort != null) {
            Data energyData = new Data(DataType.POWER, "energybalance", energyBalance, new Date());
            energyBalancePort.process(energyData);
        }
    }

    // TODO

}