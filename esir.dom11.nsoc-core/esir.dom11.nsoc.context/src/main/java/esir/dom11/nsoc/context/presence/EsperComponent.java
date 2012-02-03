package esir.dom11.nsoc.context.presence;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import org.kevoree.annotation.*;
import org.kevoree.classloader.ClassLoaderInterface;
import org.kevoree.classloader.ClassLoaderWrapper;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.osgi.framework.Bundle;


@Provides({
        @ProvidedPort(name = "presence", type = PortType.MESSAGE),
        @ProvidedPort(name = "presenceAgenda", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "compositeMessage", type = PortType.MESSAGE)
})
@DictionaryType({

})
@Library(name = "NSOC_2011")
@ComponentType
public class EsperComponent extends AbstractComponentType {

    private PresenceManager preMan;

    public EsperComponent() {
          
    }


    @Start
    public void start() {

        // get current context classloader
        // ClassLoader contextClassloader = Thread.currentThread().getContextClassLoader();
        Bundle ctx = (Bundle) this.getDictionary().get("osgi.bundle");
        ClassLoaderInterface itf = new OsgiClassLoader(ctx);
        ((ClassLoaderWrapper) ClassLoaderInterface.instance).setWrap(itf);


        preMan = new PresenceManager();
        preMan.addPresenceEventListener(new PresenceListener() {
            @Override
            public void presenceEvent(String message) {
                sendMessage(message);
            }
        });
    }

    @Stop
    public void stop() {
        preMan.stop();
    }

    @Update
    public void update() {
        preMan.stop();
        preMan = new PresenceManager();
    }


    @Port(name = "presence")
    public void presence(Object presence) {
        preMan.getCepRT().sendEvent(presence);
    }

    @Port(name = "presenceAgenda")
    public void presenceAgenda(Object agenda) {
        preMan.getCepRT().sendEvent(agenda);
    }

    public void sendMessage(String messsage) {
        if (this.isPortBinded("compositeMessage")) {
            this.getPortByName("compositeMessage", MessagePort.class).process(messsage);
        }
    }

}
