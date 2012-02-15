package esir.dom11.nsoc.context.presence;

import esir.dom11.nsoc.context.calendar.Calendar;

import java.util.EventListener;

public interface PresenceListener extends EventListener {
    public void sendAgenda (Calendar calendar);
} 