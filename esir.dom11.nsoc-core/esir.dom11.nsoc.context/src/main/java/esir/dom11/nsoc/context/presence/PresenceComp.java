package esir.dom11.nsoc.context.presence;

import esir.dom11.nsoc.context.calendar.Calendar;
import org.kevoree.annotation.*;
import org.kevoree.classloader.ClassLoaderInterface;
import org.kevoree.classloader.ClassLoaderWrapper;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.osgi.framework.Bundle;


@Provides({
        @ProvidedPort(name = "presence", type = PortType.MESSAGE),
        @ProvidedPort(name = "agenda", type = PortType.MESSAGE)
})
@Requires({
        // presence prediction
        @RequiredPort(name = "prediction", type = PortType.MESSAGE, optional = true)
})
@DictionaryType({

})
@Library(name = "NSOC_2011::Context")
@ComponentType
public class PresenceComp extends AbstractComponentType {

    private PresenceManager preMan;

    public PresenceComp() {

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
            public void sendAgenda(Calendar calendar) {
                sendPrediction(calendar);
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
        preMan.addPresenceEventListener(new PresenceListener() {

            @Override
            public void sendAgenda(Calendar calendar) {
                sendPrediction(calendar);
            }
        });
    }


    @Port(name = "presence")
    public void presence(Object presence) {
        preMan.getCepRT().sendEvent(presence);
    }

    @Port(name = "agenda")
    public void presenceAgenda(Object obj) {
        try {
            Calendar calendar = (Calendar) obj;
            preMan.setAgenda(calendar.getEvents());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public void sendPrediction(Calendar calendar) {
        if (this.isPortBinded("prediction")) {
            this.getPortByName("prediction", MessagePort.class).process(calendar);
        }
    }

}
