package esir.dom11.nsoc.context.presence;

import javax.swing.event.EventListenerList;
import java.util.Date;
import java.util.LinkedList;

public class AgendaChecker extends Thread implements AgendaCheckerListener {

    private boolean active;
    private Agenda agenda;
    protected EventListenerList listenerList;

    public AgendaChecker() {
        active = true;
        agenda = new Agenda();
        listenerList = new EventListenerList();
    }

    public Agenda getAgenda(){
        return agenda;
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
        for (AgendaEvent event : agenda.getEvents()) {
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
