package org.kevoree.service.knx;

import com.sun.org.apache.xalan.internal.xsltc.dom.AdaptiveResultTreeImpl;
import org.kevoree.annotation.*;
import org.kevoree.annotation.ComponentType;
import org.kevoree.framework.*;
import org.kevoree.annotation.Port;

/**
 * Created by IntelliJ IDEA.
 * User: michel
 * Date: 09/01/12
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */

@Provides({
        @ProvidedPort(name = "receive", type = PortType.MESSAGE)
})

@Requires ({
        @RequiredPort(name = "send", type = PortType.MESSAGE, optional = true) ,
        @RequiredPort(name = "command", type = PortType.SERVICE, className = IntTestService.class, optional = true )
})

@DictionaryType({
        @DictionaryAttribute(name = "NAME", optional = true,
                vals = {"192.168.1.128"}),
        @DictionaryAttribute(name = "TYPE OF ACTUATOR", optional = true,
                vals = {"Lamp", "Ballast", "Switch", "Shutter"}),
        @DictionaryAttribute(name = "TYPE", defaultValue = "sender", optional = true,
        vals = {"sender", "receiver"})
})

@ComponentType
public class Actuator extends AbstractComponentType{
    @Start
    public void startComponent() {
        String type = (String) this.getDictionary().get("TYPE");
        System.out.println( "\r\n" +  "\r\n" + "Actuator:: Start" + " type:" + type +"\r\n" +  "\r\n");
        System.out.println( "\r\n" + "command create" + "\r\n");
        IntTestService connectionManager = (IntTestService)this.getPortByName("command");
        System.out.println( "\r\n" + "command doing" + "\r\n");
        connectionManager.test();
        System.out.println( "\r\n" + "command done" + "\r\n");
    }

    @Stop
    public void stopComponent() {
        System.out.println("ConnectionKNX:: Stop");
    }

    @Update
    public void updateComponent() {
        System.out.println("ConnectionKNX:: Update");
        stopComponent();
        startComponent();
    }

    @Port(name = "receive")
    public void consumeHello(final Object o) {
        System.out.println("receiver:: Received " + o.toString());
        if(o instanceof String) {
            String msg = (String)o;
            System.out.println("received: " + msg);
        }
    }

}
