package knx;

import esir.dom11.nsoc.model.Action;
import org.kevoree.ComponentInstance;
import org.kevoree.ContainerNode;
import org.kevoree.DictionaryValue;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractChannelFragment;
import org.kevoree.framework.ChannelFragmentSender;
import org.kevoree.framework.NoopChannelFragmentSender;
import org.kevoree.framework.message.Message;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: michel
 * Date: 23/01/12
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
@Library(name = "NSOC_2011")
@ChannelTypeFragment
public class Dispacher extends AbstractChannelFragment {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(Dispacher.class);


    @Override
    public Object dispatch(Message msg) {
        if (getBindedPorts().isEmpty() && getOtherFragments().isEmpty()) {
            logger.warn("No consumer, msg lost=" + msg.getContent());
        }
        Action action = (Action) msg.content();
        for (org.kevoree.framework.KevoreePort p : getBindedPorts()) {
            for (ContainerNode cn : this.getModelService().getLastModel().getNodesForJ()) {
                for (ComponentInstance ci : cn.getComponentsForJ()) {
                    if (ci.getName().equals(p.getComponentName())) {
                        for (DictionaryValue dv : ci.getDictionary().get().getValuesForJ()) {
                            if (dv.getAttribute().getName().equals("Location")) {
                               // System.out.println(" ID DeviceComp: " + dv.getValue());
                                if (dv.getValue().equals(action.getActuator().getLocation())) {
                                    forward(p, msg);
                                }
                            }
                            else{
                               // if()
                            }
                        }
                    }
                }
            }
        }


        //this.getModelService().getLastModel().getNodesForJ();

        return null;
    }

    @Start
    public void startHello() {
        logger.debug("Hello Channel");
    }

    @Stop
    public void stopHello() {
        logger.debug("Bye Channel");
    }

    @Update
    public void updateHello() {
        for (String s : this.getDictionary().keySet()) {
            logger.debug("Dic => " + s + " - " + this.getDictionary().get(s));
        }
    }

    @Override
    public ChannelFragmentSender createSender(String remoteNodeName, String remoteChannelName) {
        return new NoopChannelFragmentSender();
    }

}

