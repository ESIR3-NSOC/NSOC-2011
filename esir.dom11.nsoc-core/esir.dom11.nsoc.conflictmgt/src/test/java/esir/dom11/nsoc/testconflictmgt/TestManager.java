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

   public void testReceiveCmd() throws Exception {

        LinkedList<Action> actLst1 = new LinkedList<Action>();
        LinkedList<Action> actLst2 = new LinkedList<Action>();
        actLst1.push(a1);
        actLst1.push(a2);
        actLst2.push(a1);
        actLst2.push(a2);
        c1.setActionList(actLst1);
        c2.setActionList(actLst2);
        c1.setTimeOut(delay);
        c2.setTimeOut(delay*3);
        c1.setLock(delay);
        c2.setLock(delay);
        cmdList.push(c1);
        cmdList.push(c2);

        //System.out.println(cmdList.toString());

        //System.out.println("_lastActuatorActionMap before receive: "+mng.get_lastActuatorActionMap());
        //System.out.println("_lockActuatorMap before receive: "+mng.get_lockActuatorMap());

        LinkedList<Action> actLst = mng.receiveCmd(c1);

        if (actLst != null) {
            for (Action a : actLst) {

                HashMap<UUID,Action> am = mng.get_lastActuatorActionMap();
                am.put(a.getActuator().getId(), a);
                mng.set_lastActuatorActionMap(am);

                HashMap<UUID,Long> tmp = mng.get_lockActuatorMap();
                tmp.put(a.getActuator().getId(),c1.getLock());
                mng.set_lockActuatorMap(tmp);
            }
        }

        assertFalse(mng.get_lockActuatorMap().isEmpty());
        assertTrue(mng.get_lockActuatorMap().containsKey(a1.getActuator().getId()));
        assertTrue(mng.get_lockActuatorMap().containsKey(a2.getActuator().getId()));
        assertTrue(mng.get_commandWithTimeout().isEmpty());
        //System.out.println("_lastActuatorActionMap after receive: " + mng.get_lastActuatorActionMap());
        //System.out.println("_lockActuatorMap after receive: "+mng.get_lockActuatorMap());

        longMap = mng.get_lockActuatorMap();
        longMap.remove(a1.getActuator().getId());
        mng.set_lockActuatorMap(longMap);

        actLst = mng.receiveCmd(c2);

        if (actLst != null) {
            for (Action a : actLst) {

                HashMap<UUID,Action> am = mng.get_lastActuatorActionMap();
                am.put(a.getActuator().getId(), a);
                mng.set_lastActuatorActionMap(am);

                HashMap<UUID,Long> tmp = mng.get_lockActuatorMap();
                tmp.put(a.getActuator().getId(),c2.getLock());
                mng.set_lockActuatorMap(tmp);
            }
        }

        assertFalse(mng.get_lockActuatorMap().isEmpty());
        assertTrue(!mng.get_lockActuatorMap().containsKey(a1.getActuator().getId()));
        assertTrue(mng.get_lockActuatorMap().containsKey(a2.getActuator().getId()));
        assertFalse(mng.get_commandWithTimeout().isEmpty());
        //System.out.println("_lastActuatorActionMap after receive 2: " + mng.get_lastActuatorActionMap());
        //System.out.println("_lockActuatorMap after receive 2: "+mng.get_lockActuatorMap());
        //System.out.println("_commandWithTimeout after receive 2"+mng.get_commandWithTimeout());

        longMap = mng.get_lockActuatorMap();
        longMap.clear();
        mng.set_lockActuatorMap(longMap);


        actLst = mng.receiveCmd(c1);

        if (actLst != null) {
            for (Action a : actLst) {

                HashMap<UUID,Action> am = mng.get_lastActuatorActionMap();
                am.put(a.getActuator().getId(), a);
                mng.set_lastActuatorActionMap(am);

                HashMap<UUID,Long> tmp = mng.get_lockActuatorMap();
                tmp.put(a.getActuator().getId(),c1.getLock());
                mng.set_lockActuatorMap(tmp);
            }
        }

        assertFalse(mng.get_lockActuatorMap().isEmpty());
        assertTrue(mng.get_lockActuatorMap().containsKey(a1.getActuator().getId()));
        assertTrue(mng.get_lockActuatorMap().containsKey(a2.getActuator().getId()));
        assertFalse(mng.get_commandWithTimeout().isEmpty());
        //System.out.println("_lastActuatorActionMap after receive 3: " + mng.get_lastActuatorActionMap());
        //System.out.println("_lockActuatorMap after receive 3: "+mng.get_lockActuatorMap());
        //System.out.println("_commandWithTimeout after receive 3"+mng.get_commandWithTimeout());

    }

    public void testUpdateTimeout() throws Exception {

        LinkedList<Action> actLst1 = new LinkedList<Action>();
        LinkedList<Action> actLst2 = new LinkedList<Action>();
        actLst1.push(a1);
        actLst1.push(a2);
        actLst2.push(a1);
        actLst2.push(a2);
        c1.setActionList(actLst1);
        c2.setActionList(actLst2);
        c1.setTimeOut(delay);
        c2.setTimeOut(delay*3);
        c1.setLock(delay);
        c2.setLock(delay);

        cmdList.push(c1);
        cmdList.push(c2);
        mng.set_commandWithTimeout(cmdList);

        longMap.put(a1.getActuator().getId(),c1.getLock());
        mng.set_lockActuatorMap(longMap);

        System.out.println(mng.get_commandWithTimeout().get(mng.get_commandWithTimeout().indexOf(c1)).toString());
        assertFalse(mng.isActuatorFree(a1));
        assertTrue(mng.isActuatorFree(a2));

        LinkedList<Action> actLst = mng.updateTimeout();
        if (actLst != null) {
            System.out.println("Action to send :\n" + actLst + "\n");
        }
        System.out.println("Command with timeout :\n"+mng.get_commandWithTimeout().toString()+"\n");

        actLst = mng.updateTimeout();
        if (actLst != null) {
            System.out.println("Action to send :\n" + actLst + "\n");
        }
        System.out.println("Command with timeout :\n"+mng.get_commandWithTimeout().toString()+"\n");

        longMap.remove(a1.getActuator().getId());
        mng.set_lockActuatorMap(longMap);

        actLst = mng.updateTimeout();
        if (actLst != null) {
            System.out.println("TOTO");
            System.out.println("Action to send :\n" + actLst + "\n");
        }
        System.out.println("Command with timeout :\n"+mng.get_commandWithTimeout().toString()+"\n");

    }

  /*  public void testGetPriority() throws Exception {

    }*/
}

