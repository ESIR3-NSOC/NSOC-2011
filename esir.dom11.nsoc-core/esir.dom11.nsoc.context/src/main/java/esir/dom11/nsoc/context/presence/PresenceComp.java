package esir.dom11.nsoc.context.presence;

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
            public void sendAgenda(Agenda agenda) {
                sendPrediction(agenda);
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
            public void sendAgenda(Agenda agenda) {
                sendPrediction(agenda);
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
            Agenda agenda = (Agenda) obj;
            preMan.setAgenda(agenda.getEvents());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public void sendPrediction(Agenda agenda) {
        if (this.isPortBinded("prediction")) {
            this.getPortByName("prediction", MessagePort.class).process(agenda);
        }
    }

}
