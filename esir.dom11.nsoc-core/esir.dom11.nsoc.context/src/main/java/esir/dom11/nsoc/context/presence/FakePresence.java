package esir.dom11.nsoc.context.presence;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

@Requires({
        @RequiredPort(name = "fakePresence", type = PortType.MESSAGE)
})
@Library(name = "NSOC_2011::Context")
@ComponentType
public class FakePresence extends AbstractComponentType {

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
                tempo(5000);

                // confirmation
                // 5000
                sendPresence(new PresenceEvent("1", true));
                tempo(10000);
                sendPresence(new PresenceEvent("1", false));
                // 15000

                tempo(10000);
                // 25000
                sendPresence(new PresenceEvent("1", true));
                tempo(5000); // new temporay presence
                sendPresence(new PresenceEvent("1", false));
                // 30000
                tempo(20000); // end temporay presence
                // 50000
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

}
