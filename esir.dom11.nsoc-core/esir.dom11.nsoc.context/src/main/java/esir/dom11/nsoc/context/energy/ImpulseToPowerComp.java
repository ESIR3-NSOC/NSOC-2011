package esir.dom11.nsoc.context.energy;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

@Library(name = "NSOC_2011::Context")
@ComponentType
@DictionaryType({
        @DictionaryAttribute(name = "impulseJoules", defaultValue = "4000")
})
@Provides({
        @ProvidedPort(name = "impulse", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "power", type = PortType.MESSAGE, optional = true)
})
public class ImpulseToPowerComp extends AbstractComponentType {

    private ImpulseToPower _imp;

    public ImpulseToPowerComp() {

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
        double impulseWh = Double.parseDouble((String) this.getDictionary().get("impulseJoules"));
        _imp = new ImpulseToPower(impulseWh);
    }

    @Port(name = "impulse")
    public void impulse(Object obj) {
        double power = _imp.addImpulse();
        if (power != -1.0) {
            powerProduced(power);
        }
    }

    public void powerProduced(Double power) {
        System.out.println(power);
        MessagePort powerPort = getPortByName("power", MessagePort.class);
        if (powerPort != null) {
            powerPort.process(power);
        }
    }


}
