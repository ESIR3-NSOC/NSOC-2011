package knx;

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
        @ProvidedPort(name = "fromDispacher", type = PortType.MESSAGE)
})

@Requires({
        @RequiredPort(name = "toDispacher", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "commandKNX", type = PortType.SERVICE, className = IntToConnect.class, optional = true, needCheckDependency = true)
})

@DictionaryType({
        @DictionaryAttribute(name = "TYPE_OF_ACTUATOR", defaultValue = "Lamp",optional = true,
                vals = {"Lamp", "Ballast", "Switch", "Shutter"}),
        @DictionaryAttribute(name = "ID_Actuator",optional = true)
})

@Library(name = "NSOC_2011")
@ComponentType
public class Actuator extends AbstractComponentType {
    private IntToConnect connectionManager;
    @Start
    public void startComponent() {
        System.out.println("Actuator: Start");
        String type = (String) this.getDictionary().get("TYPE_OF_ACTUATOR");
        connectionManager = getPortByName("commandKNX", IntToConnect.class);
    }

    @Stop
    public void stopComponent() {
        System.out.println("Actuator: Stop");
    }

    @Update
    public void updateComponent() {
        System.out.println("Actuator: Update");
        stopComponent();
        startComponent();
    }

    @Port(name = "fromDispacher")
    public void consumeHello(Object o) {
        Action action = (Action)o;
        System.out.println("\nActuator: Received: " + action.toString() + "\n\n");
        System.out.println("GetProtocol: " + connectionManager.getProtocol());
        if (connectionManager.getProtocol().equals("knx")) {
            connectionManager.connected();
            connectionManager.write("", Boolean.valueOf(action.getValue().toString()).booleanValue());
        }
    }
}
