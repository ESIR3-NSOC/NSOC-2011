package esir.dom11.nsoc.context.energy;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.util.Date;

@Library(name = "NSOC_2011::Context")
@ComponentType
@DictionaryType({
        @DictionaryAttribute(name = "ImpulseKWh", defaultValue = "4000")
})
@Provides({
        @ProvidedPort(name = "impulse", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "power", type = PortType.MESSAGE, optional = true)
})
public class ImpulseToPower extends AbstractComponentType {

    private Date _prev;
    private double _ImpulseKWh;

    public ImpulseToPower() {

    }

    @Start
    public void start() {
        update();
    }

    @Stop
    public void stop() {
    }

    @Update
    public void update() {
        _prev = null;
        _ImpulseKWh = Double.parseDouble((String) this.getDictionary().get("ImpulseKWh"));
    }

    @Port(name = "impulse")
    public void impulse(Object obj) {

        Date now = new Date();
        if (_prev != null) {
            long diff = now.getTime() - _prev.getTime();

            // example :
            // 1kWh = 3.6MJ
            // with 4000 imp per kWh = 900 J / impulse
            double impulseEnergy = 3.6e9 / _ImpulseKWh;

            // power = energy (J) / time (s)
            double power = impulseEnergy / (diff / 1000);
            powerProduced(power);

        } else {
            _prev = (Date) now.clone();
        }
    }


    public void powerProduced(Double power) {
        MessagePort powerPort = getPortByName("power", MessagePort.class);
        if (powerPort != null) {
            powerPort.process(power);
        }
    }
}
