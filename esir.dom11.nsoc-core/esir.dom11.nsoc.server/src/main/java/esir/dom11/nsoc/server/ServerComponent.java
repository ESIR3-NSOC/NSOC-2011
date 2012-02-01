package esir.dom11.nsoc.server;

/**
 * Created by IntelliJ IDEA.
 * User: Pierre
 * Date: 07/01/12
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */

import esir.dom11.nsoc.model.Data;
import org.kevoree.annotation.*;
import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.Port;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.util.LinkedList;

@Library(name = "NSOC_2011")

// output port (HMI -> CTRL)
@Requires({
        @RequiredPort(name = "HMI", type = PortType.MESSAGE)
})

// input port (CTRL -> HMI)
@Provides({
        @ProvidedPort(name = "CTRL", type = PortType.MESSAGE)
})

@DictionaryType({
        @DictionaryAttribute(name = "port", defaultValue = "8182", optional = true)
})

@ComponentType
public class ServerComponent extends AbstractComponentType {
    private ServerManager _manager;

    public ServerComponent() {
         _manager = new ServerManager(this);
    }

    @Start
    public void startComponent(){
        int port = Integer.valueOf(getDictionary().get("port").toString());
        _manager.startServer(port)  ;
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

    @Port(name = "CTRL")
    public void receiveToIhm(Object dataList){
       //make test on the object
       LocalStorage.getLocalStorageObject().setAllData((LinkedList<Data>) dataList);
    }

    //send the message through the require port
    public void sendMessage(Object message){
        MessagePort prodPort = getPortByName("HMI", MessagePort.class);
        if(prodPort != null){
            prodPort.process(message);
        }
     }

}
