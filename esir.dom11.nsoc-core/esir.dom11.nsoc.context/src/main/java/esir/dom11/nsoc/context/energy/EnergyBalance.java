package esir.dom11.nsoc.context.energy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *      This class generate a report about energy balance ( local production - consumption )
 */
public class EnergyBalance {

    private static Logger logger = LoggerFactory.getLogger(EnergyBalance.class);

    private double _consumption;
    private double _production;

    public EnergyBalance() {
        _consumption = 0.0;
        _production = 0.0;
    }

    public EnergyBalance(double consumption, double production) {
        _consumption = consumption;
        _production = production;
    }

    public double getConsumption() {
        return _consumption;
    }

    public void setConsumption(double consumption) {
        _consumption = consumption;
    }

    public double getProduction() {
        return _production;
    }

    public void setProduction(double production) {
        _production = production;
    }

    public double getEnergyBalance() {
        return _production - _consumption;
    }
}

