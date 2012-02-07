package esir.dom11.nsoc.context.presence;

import java.util.EventListener;

public interface PresenceListener extends EventListener {
    public void presenceEvent (String message);
} 