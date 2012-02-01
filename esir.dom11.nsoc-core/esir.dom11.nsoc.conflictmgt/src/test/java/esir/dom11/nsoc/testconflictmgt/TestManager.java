package esir.dom11.nsoc.testconflictmgt;

import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.Command;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import esir.dom11.nsoc.conflictmgt.Manager;

public class TestManager extends TestCase {

    private long delay = 100;
    private Manager mng;
    private HashMap<UUID,Action> actionMap;
    private LinkedList<Command> cmdList;
    private HashMap<UUID, Long> longMap;
    private Action a1;
    private Action a2;
    private Command c1;
    private Command c2;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mng = new Manager(delay);
        a1 = new Action(UUID.randomUUID(),1);
        a2 = new Action(UUID.randomUUID(),2);
        c1 = new Command();
        c2 = new Command();
        actionMap = new HashMap<UUID, Action>();
        cmdList = new LinkedList<Command>();
        longMap = new HashMap<UUID, Long>();
    }

    public void testIsActuatorFree() throws Exception {
        actionMap = mng.get_lastActuatorActionMap();
        actionMap.put(a1.getIdActuator(), a1);
        mng.set_lastActuatorActionMap(actionMap);
        assertFalse(mng.isActuatorFree(a1));
        assertTrue(mng.isActuatorFree(a2));
    }

    public void testUpdateLock() throws Exception {

        longMap.put(a1.getIdActuator(),delay*2);
        longMap.put(a2.getIdActuator(),delay*3);
        mng.set_lockActuatorMap(longMap);

        mng.updateLock();
        long tmp = mng.get_lockActuatorMap().get(a1.getIdActuator());
        assertEquals(tmp, delay);
        tmp = mng.get_lockActuatorMap().get(a2.getIdActuator());
        assertEquals(tmp,delay*2);

        /*mng.updateLock();
        assertFalse(mng.get_lockActuatorMap().containsKey(a1.getIdActuator()));
        assertTrue(mng.get_lockActuatorMap().containsKey(a2.getIdActuator()));
        */
    }

    public void testUpdateTimeout() throws Exception {

    }

    public void testGetPriority() throws Exception {

    }
}

