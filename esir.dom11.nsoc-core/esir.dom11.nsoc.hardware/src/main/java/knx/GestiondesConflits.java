package knx;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

/**
 * Created by IntelliJ IDEA.
 * User: michel
 * Date: 25/01/12
 * Time: 08:37
 * To change this template use File | Settings | File Templates.
 */
@Requires({
        @RequiredPort(name = "prod", type = PortType.MESSAGE, optional = true)
})
@DictionaryType({
        @DictionaryAttribute(name = "ID_Actuator",optional = true),
        @DictionaryAttribute(name = "Value",optional = true)
})

@Library(name = "NSOC_2011")
@ComponentType
public class GestiondesConflits extends AbstractComponentType {

     private Boolean stop = true;
     private String ID_Actuator;
     private Object Value;
    @Start
    public void startComponent() {
        System.out.print("GestiondesConflits: start\n");
        ID_Actuator = (String) this.getDictionary().get("ID_Actuator");
        Value = (Object)this.getDictionary().get("Value");
        System.out.print("Value : " + Value);
        sendBackgroundMessage();
    }

    @Stop
    public void stopComponent() {
        System.out.print("GestiondesConflits: stop\n");
        stop = false;
    }

    @Update
    public void updateComponent() {
        System.out.print("GestiondesConflits: update\n");
        stopComponent();
        startComponent();
    }

    public void sendMessage(Object message) {
        MessagePort prodPort = getPortByName("prod", MessagePort.class);
        if (prodPort != null) {
            prodPort.process(message);
        }
    }

    public void sendBackgroundMessage() {
        new Thread() {
            @Override
            public void run() {

                int i = 0;
                while (stop) {
                    try {
                        i++;
                        Action action = new Action(ID_Actuator, Value);
                        sendMessage(action);
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }.start();
    }
}



