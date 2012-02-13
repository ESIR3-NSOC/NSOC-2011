package knx;

import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Sensor;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;


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
        @RequiredPort(name = "DataSend", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "commandKNX", type = PortType.SERVICE, className = IntToConnect.class, optional = true, needCheckDependency = true)
})

@DictionaryType({
        @DictionaryAttribute(name = "DEVICE", defaultValue = "Actuator", optional = true,
                vals = {"Sensor", "Actuator"}),
        @DictionaryAttribute(name = "LOCATION", defaultValue = "/bat7/salle930/0", optional = true),
        @DictionaryAttribute(name = "AddressDevice", optional = true)
})

@Library(name = "NSOC_2011")
@ComponentType
public class DeviceComp extends AbstractComponentType {
    private IntToConnect connectionManager;
    private Actuator mActuator;
    private Sensor mSensor;
    private String mDevice;
    private String mLocation;
    private String mAddressDevice;
    private Boolean stop = false;

    @Start
    public void startComponent() {
        System.out.println("DeviceComp: Start");
        mDevice = (String) this.getDictionary().get("DEVICE");
        mLocation = (String) this.getDictionary().get("LOCATION");
        mAddressDevice = (String) this.getDictionary().get("AddressDevice");
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
            sendBackgroundMessage();
        }
    }

    @Stop
    public void stopComponent() {
        System.out.println("DeviceComp: Stop");
        stop = true;
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
                connectionManager.write(mAddressDevice, Boolean.valueOf(action.getValue()).booleanValue());
            }
        }
    }

    public void sendMessage(Object message) {
        MessagePort prodPort = getPortByName("DataSend", MessagePort.class);
        if (prodPort != null) {
            prodPort.process(message);
        }
    }

    public void sendBackgroundMessage() {
        new Thread() {
            @Override
            public void run() {

                while (!stop) {
                    try {
                        connectionManager.read(mAddressDevice);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }.start();
    }
}
