package esir.dom11.nsoc.context.presence;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

@Requires({
        @RequiredPort(name = "fakePresence", type = PortType.MESSAGE),
        @RequiredPort(name = "fakePresenceAgenda", type = PortType.MESSAGE)
})
@Library(name = "NSOC_2011::Context")
@ComponentType
public class PresenceTestComp extends AbstractComponentType {

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
                sendPresence(new PresenceEvent("1", true));
                tempo(200);
                sendPresenceAgenda(new PresenceAgendaEvent("1", true));
                tempo(2000); // confirmation
                sendPresence(new PresenceEvent("1", false));
                tempo(2000); // end presence
                sendPresenceAgenda(new PresenceAgendaEvent("1", false));
                tempo(2000);
                sendPresence(new PresenceEvent("1", true));
                tempo(2000); // new temporay presence
                sendPresence(new PresenceEvent("1", false));
                tempo(2000); // end temporay presence
                sendPresenceAgenda(new PresenceAgendaEvent("1", true));
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

    public void sendPresence(PresenceEvent pres) {
        if (this.isPortBinded("fakePresence")) {
            this.getPortByName("fakePresence", MessagePort.class).process(pres);
        }
    }

    public void sendPresenceAgenda(PresenceAgendaEvent pres) {
        if (this.isPortBinded("fakePresenceAgenda")) {
            this.getPortByName("fakePresenceAgenda", MessagePort.class).process(pres);
        }
    }

}
