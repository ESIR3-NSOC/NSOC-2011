package esir.dom11.nsoc.testconflictmgt;

import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.Command;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.device.Actuator;
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
        a1 = new Action(new Actuator(UUID.randomUUID(), DataType.UNKNOWN,""),"1");
        a2 = new Action(new Actuator(UUID.randomUUID(), DataType.UNKNOWN,""),"2");
        c1 = new Command();
        c2 = new Command();
        actionMap = new HashMap<UUID, Action>();
        cmdList = new LinkedList<Command>();
        longMap = new HashMap<UUID, Long>();
    }

    public void testIsActuatorFree() throws Exception {
        longMap = mng.get_lockActuatorMap();
        longMap.put(a1.getActuator().getId(), delay);
        mng.set_lockActuatorMap(longMap);
        assertFalse(mng.isActuatorFree(a1));
        assertTrue(mng.isActuatorFree(a2));
    }

    public void testUpdateLock() throws Exception {

        longMap.put(a1.getActuator().getId(),delay*2);
        longMap.put(a2.getActuator().getId(),delay*3);
        mng.set_lockActuatorMap(longMap);

        mng.updateLock();
        long tmp = mng.get_lockActuatorMap().get(a1.getActuator().getId());
        assertEquals(tmp, delay);
        tmp = mng.get_lockActuatorMap().get(a2.getActuator().getId());
        assertEquals(tmp,delay*2);

        mng.updateLock();
        assertFalse(mng.get_lockActuatorMap().containsKey(a1.getActuator().getId()));
        assertTrue(mng.get_lockActuatorMap().containsKey(a2.getActuator().getId()));

    }

    public void testUpdateTimeout() throws Exception {

    }

    public void testGetPriority() throws Exception {

    }
}

