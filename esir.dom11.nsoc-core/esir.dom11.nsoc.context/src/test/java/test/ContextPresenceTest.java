package test;

import esir.dom11.nsoc.context.presence.Presence;
import esir.dom11.nsoc.context.presence.PresenceAgenda;
import esir.dom11.nsoc.context.presence.PresenceManager;
import junit.framework.TestCase;

public class ContextPresenceTest extends TestCase {

    public void testGeneral() {


        PresenceManager presMan =  new PresenceManager();

        presMan.getCepRT().sendEvent(new PresenceAgenda("1", true));
        presMan.getCepRT().sendEvent(new Presence("1", true));
        tempo(2000);
        presMan.getCepRT().sendEvent(new Presence("1", false));
        tempo(2000);
        presMan.getCepRT().sendEvent(new PresenceAgenda("1", false));
        tempo(2000);
        presMan.getCepRT().sendEvent(new Presence("1", false));
        tempo(2000);
        presMan.getCepRT().sendEvent(new Presence("1", true));
        tempo(2000);
        presMan.getCepRT().sendEvent(new Presence("1", false));
        tempo(2000);
        presMan.getCepRT().sendEvent(new PresenceAgenda("1", true));
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
