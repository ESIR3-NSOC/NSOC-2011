package esir.dom11.nsoc.conflictmgt;

import esir.dom11.nsoc.model.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

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
    public ExecTimer (int corePoolSize, Manager mng, long delay){

        super(corePoolSize);

        _delay = delay;
        _mng = mng;

        this.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run(){
                _mng.updateLock();
                try {
                    sleep(_delay/2);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    System.out.println("Exception caught: " + e);
                }
                LinkedList<Action> actLst = _mng.updateTimeout();
                for(Action a: actLst){
                    _mng._conflict.send2Actuator(a);
                }
            }
        }, 0, _delay, TimeUnit.MILLISECONDS);
    }
}
