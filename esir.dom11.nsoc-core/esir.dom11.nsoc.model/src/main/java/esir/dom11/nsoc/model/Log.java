package esir.dom11.nsoc.model;

import java.util.Date;
import java.util.UUID;

public class Log {
    
    /*
     * Attributes
     */
    
    private final UUID _id;
    private final String _message;
    private final Date _date;
    private final LogLevel _logLevel;
    
    /*
     * Constructors
     */

    public Log() {
        _id = UUID.fromString("00000000-0000-0000-0000-000000000000");
        _date = new Date();
        _message = "";
        _logLevel = LogLevel.INFO;
    }

    public Log(String message, LogLevel logLevel) {
        _id = UUID.randomUUID();
        _date = new Date();
        _message = message;
        _logLevel = logLevel;
    }

    public Log(UUID id, Date date, String message, LogLevel logLevel) {
        _id = id;
        _date = date;
        _message = message;
        _logLevel = logLevel;
    }
    
    /*
     * Getters
     */

    public UUID getId() {
        return _id;
    }

    public String getMessage() {
        return _message;
    }

    public Date getDate() {
        return _date;
    }

    public LogLevel getLogLevel() {
        return _logLevel;
    }
    
    /*
     * Overrides
     */

    @Override
    public String toString() {
        return "\n* * * Log " + getId() + " * * *"
                + "\nLog Lavel: " + getLogLevel().getValue()
                + "\nDate: " + getDate()
                + "\nMessage: " + getMessage() + "\n";
    }
}
