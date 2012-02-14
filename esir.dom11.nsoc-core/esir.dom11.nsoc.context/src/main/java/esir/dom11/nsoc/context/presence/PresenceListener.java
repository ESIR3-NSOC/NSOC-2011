package esir.dom11.nsoc.context.presence;

import java.util.EventListener;

public interface PresenceListener extends EventListener {
    public void sendAgenda (Agenda agenda);
} 