package esir.server; /**
 * Created by IntelliJ IDEA.
 * User: Pierre
 * Date: 07/01/12
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */

import org.kevoree.annotation.*;
import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.Port;
import org.kevoree.framework.*;

// output port (IHM -> CTRL)
@Requires({
        @RequiredPort(name = "fromIhm", type = PortType.MESSAGE, optional = true)
})

// input port (CTRL -> IHM)
@Provides({
        @ProvidedPort(name = "toIhm", type = PortType.MESSAGE)
})

@ComponentType
public class ServerComponent extends AbstractComponentType{
    private ServerGui sgui;

    @Start
    public void startComponent(){
        sgui = new ServerGui();
    }

    @Stop
    public void stopComponent(){
        sgui.stopGui();
    }

    @Update
    public void updateComponent(){
           stopComponent();
           startComponent();
    }

    @Port(name = "toIhm")
    public void receiveToIhm(Object o){

    }

     public void sendMessage(String mess){
        MessagePort prodPort = getPortByName("fromIhm", MessagePort.class);
        if(prodPort != null){
            prodPort.process(mess);
        }
     }

}
