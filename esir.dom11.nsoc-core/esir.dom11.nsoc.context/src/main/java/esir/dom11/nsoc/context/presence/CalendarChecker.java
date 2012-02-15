package esir.dom11.nsoc.context.presence;

import esir.dom11.nsoc.context.calendar.Calendar;
import esir.dom11.nsoc.context.calendar.CalendarEvent;

import javax.swing.event.EventListenerList;
import java.util.Date;

public class CalendarChecker extends Thread implements CalendarCheckerListener {

    private boolean active;
    private Calendar calendar;
    protected EventListenerList listenerList;

    public CalendarChecker() {
        active = true;
        calendar = new Calendar();
        listenerList = new EventListenerList();
    }

    public Calendar getCalendar() {
        return calendar;
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
        for (CalendarEvent event : calendar.getEvents()) {
            if (Math.abs(
                    date.getTime() - event.getDateStart().getTime()) < 500) {
                // start of an event
                eventStart();
            } else if (Math.abs(
                    date.getTime() - event.getDateEnd().getTime()) < 500) {
                // end of an event
                eventStop();
            }
        }
    }

    public void addAgendaEventListener(CalendarCheckerListener l) {
        this.listenerList.add(CalendarCheckerListener.class, l);
    }

    @Override
    public void eventStart() {
        CalendarCheckerListener[] listeners = (CalendarCheckerListener[])
                listenerList.getListeners(CalendarCheckerListener.class);
        for (int i = listeners.length - 1; i >= 0; i--) {
            listeners[i].eventStart();
        }
    }

    @Override
    public void eventStop() {
        CalendarCheckerListener[] listeners = (CalendarCheckerListener[])
                listenerList.getListeners(CalendarCheckerListener.class);
        for (int i = listeners.length - 1; i >= 0; i--) {
            listeners[i].eventStop();
        }
    }
}
