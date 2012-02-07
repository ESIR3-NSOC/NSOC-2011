package knx;

import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Sensor;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;


/**
 * Created by IntelliJ IDEA.
 * User: michel
 * Date: 09/01/12
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */

@Provides({
        @ProvidedPort(name = "ActionReceive", type = PortType.MESSAGE)
})

@Requires({
        @RequiredPort(name = "toDispacher", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "commandKNX", type = PortType.SERVICE, className = IntToConnect.class, optional = true, needCheckDependency = true)
})

@DictionaryType({
        @DictionaryAttribute(name = "DEVICE", defaultValue = "Actuator", optional = true,
                vals = {"Sensor", "Actuator"}),
        @DictionaryAttribute(name = "LOCATION", defaultValue = "/bat7/salle930/0", optional = true),
        @DictionaryAttribute(name = "AddressActuator", optional = true)
})

@Library(name = "NSOC_2011")
@ComponentType
public class DeviceComp extends AbstractComponentType {
    private IntToConnect connectionManager;
    private Actuator mActuator;
    private Sensor mSensor;
    private String mDevice;
    private String mLocation;
    private String mAddressActuator;

    @Start
    public void startComponent() {
        System.out.println("DeviceComp: Start");
        mDevice = (String) this.getDictionary().get("DEVICE");
        mLocation = (String) this.getDictionary().get("LOCATION");
        mAddressActuator = (String) this.getDictionary().get("AddressActuator");
        connectionManager = getPortByName("commandKNX", IntToConnect.class);

        if (connectionManager.getProtocol().equals("knx")) {
            connectionManager.connected();
        }
        if (mDevice.equals("Actuator")) {
            System.out.println("DeviceComp: Create Actuator");
            mActuator = new Actuator(DataType.UNKNOWN, mLocation);
        } else if (mDevice.equals("Sensor")) {
            System.out.println("DeviceComp: Create Sensor");
            mSensor = new Sensor(DataType.UNKNOWN, mLocation);
        }
    }

    @Stop
    public void stopComponent() {
        System.out.println("DeviceComp: Stop");
    }

    @Update
    public void updateComponent() {
        System.out.println("DeviceComp: Update");
        stopComponent();
        startComponent();
    }

    @Port(name = "ActionReceive")
    public void consumeHello(Object o) {
        Action action = (Action) o;
        System.out.println("\nDeviceComp: Received: " + action.toString() + "\n\n");
        System.out.println("GetProtocol: " + connectionManager.getProtocol());
        if (connectionManager.getProtocol().equals("knx")) {
            if (mDevice.equals("Actuator")) {
                System.out.println("DeviceComp: Receive Action");
                connectionManager.write(mAddressActuator, Boolean.valueOf(action.getValue().toString()).booleanValue());
            } else if (mDevice.equals("Sensor")) {
                System.out.println("DeviceComp: Send Data");
            }
        }
    }
}
