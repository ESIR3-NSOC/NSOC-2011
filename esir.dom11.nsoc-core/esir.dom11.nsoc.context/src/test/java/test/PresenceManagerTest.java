package test;

import esir.dom11.nsoc.context.presence.PresenceAgendaEvent;
import esir.dom11.nsoc.context.presence.PresenceEvent;
import esir.dom11.nsoc.context.presence.PresenceManager;
import junit.framework.TestCase;

public class PresenceManagerTest extends TestCase {

    public void testGeneral() {
        PresenceManager presMan = new PresenceManager();

        presMan.getCepRT().sendEvent(new PresenceAgendaEvent("1", true));
        presMan.getCepRT().sendEvent(new PresenceEvent("1", true));
        tempo(2000);
        presMan.getCepRT().sendEvent(new PresenceEvent("1", false));
        tempo(2000);
        presMan.getCepRT().sendEvent(new PresenceAgendaEvent("1", false));
        tempo(2000);
        presMan.getCepRT().sendEvent(new PresenceEvent("1", false));
        tempo(2000);
        presMan.getCepRT().sendEvent(new PresenceEvent("1", true));
        tempo(2000);
        presMan.getCepRT().sendEvent(new PresenceEvent("1", false));
        tempo(2000);
        presMan.getCepRT().sendEvent(new PresenceAgendaEvent("1", true));
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
