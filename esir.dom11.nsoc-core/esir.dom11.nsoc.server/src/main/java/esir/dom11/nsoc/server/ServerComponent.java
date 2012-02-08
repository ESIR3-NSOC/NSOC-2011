package esir.dom11.nsoc.server;

/**
 * Created by IntelliJ IDEA.
 * User: Pierre
 * Date: 07/01/12
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */

import esir.dom11.nsoc.ctrl.Control;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.HmiRequest;
import org.kevoree.annotation.*;
import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.Port;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.util.LinkedList;

@Library(name = "NSOC_2011")


// input port (CTRL -> HMI)
@Requires({
        @RequiredPort(name = "CTRL", type = PortType.SERVICE, className = Control.class)
})

@DictionaryType({
        @DictionaryAttribute(name = "port", defaultValue = "8182", optional = true)
})

@ComponentType
public class ServerComponent extends AbstractComponentType {
    private ServerManager _manager;

    public ServerComponent() {
        // save itself in LocalStorage to be reused in ServerManager class
        LocalStorage.getLocalStorageObject().setServerComponent(this);
        _manager = new ServerManager();
    }

    @Start
    public void startComponent(){
        int port = Integer.valueOf(getDictionary().get("port").toString());
        _manager.startServer(port);
    }

    @Stop
    public void stopComponent(){
        _manager.stopServer();
    }

    @Update
    public void updateComponent(){
        stopComponent();
        startComponent();
    }


    public LinkedList<Data> sendGetRequest(HmiRequest hmir){
         return (LinkedList<Data>) getPortByName("CTRL", Control.class).receiveHMI(hmir, "getFromHmi");
    }


    //send the message through the require port
    public void sendMessage(Object message){
        MessagePort prodPort = getPortByName("HMI", MessagePort.class);
        if(prodPort != null){
            prodPort.process(message);
        }
     }

}
