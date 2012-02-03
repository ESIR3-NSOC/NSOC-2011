package esir.dom11.nsoc.conflictmgt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class ExecTimer extends ScheduledThreadPoolExecutor {

    /*
    * Class Attributes
    */
    private static Logger logger = LoggerFactory.getLogger(ExecTimer.class.getName());

    /*
     * Attributes
     */
    private Manager _mng;
    private long _delay = 60000;

    /*
     *  Constructor
     */
    public ExecTimer(int corePoolSize, Manager mng, long delay) {

        super(corePoolSize);

        _delay = delay;
        _mng = mng;

        this.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                _mng.updateLock();
                _mng.updateTimeout();
            }
        }, 0, _delay, TimeUnit.MILLISECONDS);
    }

    /*
     * Methods
     */

}
