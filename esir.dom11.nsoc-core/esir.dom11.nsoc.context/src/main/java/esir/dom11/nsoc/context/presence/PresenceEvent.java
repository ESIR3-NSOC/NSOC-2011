package esir.dom11.nsoc.context.presence;


public class PresenceEvent {
    String name;
    boolean presence;

    public PresenceEvent() {

    }

    public PresenceEvent(String name, boolean presence) {
        this.name = name;
        this.presence = presence;
    }

    public String getName() {
        return name;
    }

    public boolean isPresence() {
        return presence;
    }

    @Override
    public String toString() {
        return "name:"+this.name+" presence:"+this.presence;
    }
}
