package esir.dom11.nsoc.context.presence;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

@Requires({
        @RequiredPort(name = "fakePresence", type = PortType.MESSAGE),
        @RequiredPort(name = "fakePresenceAgenda", type = PortType.MESSAGE)
})
@Library(name = "Kevoree::Esper")
@ComponentType
public class EsperTestComponent extends AbstractComponentType {

    @Start
    public void start() {
        GeneratePresence();
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {
        GeneratePresence();
    }

    public void sendPresence(Presence pres) {
        if (this.isPortBinded("fakePresence")) {
            this.getPortByName("fakePresence", MessagePort.class).process(pres);
        }
    }

    public void sendPresenceAgenda(PresenceAgenda pres) {
        if (this.isPortBinded("fakePresenceAgenda")) {
            this.getPortByName("fakePresenceAgenda", MessagePort.class).process(pres);
        }
    }

    public void GeneratePresence() {
        sendPresenceAgenda(new PresenceAgenda("1", true));
        sendPresence(new Presence("1", true));
        tempo(2000);
        sendPresence(new Presence("1", false));
        tempo(2000);
        sendPresenceAgenda(new PresenceAgenda("1", false));
        tempo(2000);
        sendPresence(new Presence("1", false));
        tempo(2000);
        sendPresence(new Presence("1", true));
        tempo(2000);
        sendPresence(new Presence("1", false));
        tempo(2000);
        sendPresenceAgenda(new PresenceAgenda("1", true));
        tempo(2000);

    }

    public void tempo(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
