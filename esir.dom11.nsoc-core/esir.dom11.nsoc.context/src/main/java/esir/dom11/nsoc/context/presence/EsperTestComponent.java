package esir.dom11.nsoc.context.presence;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

@Requires({
        @RequiredPort(name = "fakePresence", type = PortType.MESSAGE),
        @RequiredPort(name = "fakePresenceAgenda", type = PortType.MESSAGE)
})
@Library(name = "NSOC_2011")
@ComponentType
public class EsperTestComponent extends AbstractComponentType {

    @Start
    public void start() {
        update();
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {
        Thread th = new Thread() {

            public void GeneratePresence() {
                tempo(2000);
                sendPresence(new Presence("1", true));
                tempo(200);
                sendPresenceAgenda(new PresenceAgenda("1", true));
                tempo(2000); // confirmation
                sendPresence(new Presence("1", false));
                tempo(2000); // end presence
                sendPresenceAgenda(new PresenceAgenda("1", false));
                tempo(2000);
                sendPresence(new Presence("1", true));
                tempo(2000); // new temporay presence
                sendPresence(new Presence("1", false));
                tempo(2000); // end temporay presence
                sendPresenceAgenda(new PresenceAgenda("1", true));
                tempo(2000);  // cancel


            }

            public void tempo(long ms) {
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void run() {
                GeneratePresence();
            }
        };
        th.start();
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

}
