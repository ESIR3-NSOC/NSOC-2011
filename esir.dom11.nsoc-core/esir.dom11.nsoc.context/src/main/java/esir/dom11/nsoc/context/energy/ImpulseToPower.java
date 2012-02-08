package esir.dom11.nsoc.context.energy;

import java.util.Date;

public class ImpulseToPower {

    private Date _prev;
    private double _impulseJoules;

    // example :
    // 1kWh = 3.6MJ
    // with 4000 imp per kWh = 900 J / impulse
    public ImpulseToPower(double impulseJoules) {
        Date _prev = null;
        _impulseJoules = impulseJoules;
    }

    /**
     * return average power
     *
     * @return
     */
    public double addImpulse() {
        Date now = new Date();
        if (_prev != null) {
            double diff = (double)(now.getTime() - _prev.getTime());


            // power = energy (J) / time (s)
            double power = (_impulseJoules / (diff/1000));
            _prev = now;
            return power;
        } else {
            _prev = now;
            return -1.0;
        }
    }

}
