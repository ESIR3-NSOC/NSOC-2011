package esir.dom11.nsoc.model;

import javax.swing.event.EventListenerList;
import java.util.Date;
import java.util.LinkedList;

public class AgendaChecker extends Thread implements AgendaCheckerListener {

    private boolean active;
    private LinkedList<AgendaEvent> events;
    protected EventListenerList listenerList;

    public AgendaChecker() {
        active = true;
        events = new LinkedList<AgendaEvent>();
        listenerList = new EventListenerList();
    }

    public AgendaChecker(LinkedList<AgendaEvent> events) {
        active = true;
        this.events = (LinkedList<AgendaEvent>)events.clone();
        listenerList = new EventListenerList();
    }


    public LinkedList<AgendaEvent> getEvents() {
        return events;
    }

    public void setEvents(LinkedList<AgendaEvent> events) {
        this.events = events;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void run() {
        while (active) {
            try {
                Thread.sleep(1000);
                checkEvents(new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void checkEvents(Date date) {
        for (AgendaEvent event : this.events) {
            if (Math.abs(
                    date.getTime() - event.getStart().getTime()) < 500) {
                // start of an event
                eventStart();
            } else if (Math.abs(
                    date.getTime() - event.getEnd().getTime()) < 500) {
                // end of an event
                eventStop();
            }
        }
    }

    public void addAgendaEventListener(AgendaCheckerListener l) {
        this.listenerList.add(AgendaCheckerListener.class, l);
    }

    @Override
    public void eventStart() {
        AgendaCheckerListener[] listeners = (AgendaCheckerListener[])
                listenerList.getListeners(AgendaCheckerListener.class);
        for (int i = listeners.length - 1; i >= 0; i--) {
            listeners[i].eventStart();
        }
    }

    @Override
    public void eventStop() {
        AgendaCheckerListener[] listeners = (AgendaCheckerListener[])
                listenerList.getListeners(AgendaCheckerListener.class);
        for (int i = listeners.length - 1; i >= 0; i--) {
            listeners[i].eventStop();
        }
    }
}
