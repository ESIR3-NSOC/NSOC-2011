package esir.dom11.nsoc.context.energy;

import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.device.Sensor;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.util.Date;
import java.util.UUID;


@Library(name = "NSOC_2011::Context")
@ComponentType
@Provides({
        @ProvidedPort(name = "production", type = PortType.MESSAGE),
        @ProvidedPort(name = "consumption", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "energyBalance", type = PortType.MESSAGE, optional = true)
})
public class EnergyBalanceComp extends AbstractComponentType {

    private double _consumption = 0.0;
    private double _production = 0.0;

    @Start
    public void start() {
    }

    @Stop
    public void stop() {
    }

    @Update
    public void update() {

    }


    @Port(name = "consumption")
    public void consumption(Object obj) {
        Data data = (Data) obj;
        DataType dataType = data.getSensor().getDataType();

        if (dataType == DataType.POWER) {
            _consumption = Double.parseDouble(data.getValue());
        }
        energyBalanceProduced(_consumption - _production);
    }

    @Port(name = "production")
    public void production(Object obj) {
        Data data = (Data) obj;
        DataType dataType = data.getSensor().getDataType();

        if (dataType == DataType.POWER) {
            _production = Double.parseDouble(data.getValue());
        }
        energyBalanceProduced(_consumption - _production);
    }

    public void energyBalanceProduced(Double energyBalance) {
        MessagePort energyBalancePort = getPortByName("energyBalance", MessagePort.class);
        if (energyBalancePort != null) {
            Data energyData = new Data(
                    UUID.randomUUID(),
                    new Sensor(UUID.randomUUID(),
                            DataType.POWER,
                            "context"),
                    Double.toString(energyBalance),
                    new Date());
            energyBalancePort.process(energyData);
        }
    }

}