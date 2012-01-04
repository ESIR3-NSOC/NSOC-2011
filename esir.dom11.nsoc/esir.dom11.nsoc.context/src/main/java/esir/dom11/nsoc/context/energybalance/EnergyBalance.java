package esir.dom11.nsoc.context.energybalance;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author: nicolas riche
 */
@Library(name = "NSOC_2011")
@ComponentType
@Provides({
        @ProvidedPort(name = "consumption", type = PortType.MESSAGE),
        @ProvidedPort(name = "production", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "balance", type = PortType.MESSAGE, optional = true)
})
public class EnergyBalance extends AbstractComponentType {

    /*
    * Class Attributes
    */
    private static Logger logger = LoggerFactory.getLogger(EnergyBalance.class);

    /*
    * Attributes
    */
    private double _consumption;
    private double _production;

    public EnergyBalance() {
        _consumption = 0.0;
        _production = 0.0;
    }

    /*
     * Getters / Setters
     */

    /*
    * Overrides
    */

    @Start
    public void start() {
        logger.info("= = = = = start context-production/consumption report = = = = = =");
    }

    @Stop
    public void stop() {
        logger.info("= = = = = stop context-production/consumption report = = = = = =");
    }

    @Update
    public void update() {
        logger.info("= = = = = update context-production/consumption report = = = = = =");
    }


    @Port(name = "consumption")
    public void energyConsumption(Object obj) {
        try {
            _consumption = (Double) obj;
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }

    @Port(name = "production")
    public void energyProduction(Object obj) {
        try {
            _production = (Double) obj;
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }

}
