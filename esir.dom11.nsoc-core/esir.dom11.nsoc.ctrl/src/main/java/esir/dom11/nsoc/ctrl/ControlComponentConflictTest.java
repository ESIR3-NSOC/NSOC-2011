package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.*;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.service.IDbService;
import esir.dom11.nsoc.service.IServerService;
import esir.dom11.nsoc.service.RequestResult;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Anthony
 * Date: 12/02/12
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */



@Library(name = "NSOC_2011")
@ComponentType
@Provides({
        @ProvidedPort(name = "RHMI", type = PortType.SERVICE, className = IServerService.class),
        @ProvidedPort(name = "postFromHmi", type = PortType.MESSAGE),
        @ProvidedPort(name = "presenceFromContext", type = PortType.MESSAGE),
        @ProvidedPort(name = "RConflict", type = PortType.MESSAGE),
        @ProvidedPort(name = "RSensors", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "HMII", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "Context", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "DAO", type = PortType.SERVICE, className = IDbService.class, needCheckDependency = true, optional = true),
        @RequiredPort(name = "Conflict", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "Sensors", type = PortType.MESSAGE, optional = true)
})

public class ControlComponentConflictTest extends AbstractComponentType implements ctrlInterface, IServerService {
    private TheBrain theBrain;
    private LinkedList<Command> commandList;
    private LinkedList<AgendaEvent> agendaList;
    private AgendaChecker agendaChecker;

    @Start
    public void start() {
        System.out.println("Control : Start");

        agendaList = new LinkedList<AgendaEvent>();
        commandList = new LinkedList<Command>();


        LinkedList<Action> list = new LinkedList<Action>();
        Actuator actuator = new Actuator(DataType.SWITCH, "/bat7/salle930/lamp/0");
        Action action = new Action(actuator, "true");

        list.add(action);
        Command command = new Command(list, Category.USER, (long) 0, (long) 0);
        //send command
        send2Conflict(command);

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //--------
        LinkedList<Action> list2 = new LinkedList<Action>();
        Actuator actuator2 = new Actuator(DataType.SWITCH, "/bat7/salle930/lamp/0");
        Action action2 = new Action(actuator2, "false");

        list.add(action2);
        Command command2 = new Command(list2, Category.USER, (long) 0, (long) 0);
        //send command
        send2Conflict(command2);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    @Stop
    public void stop() {
/*        System.out.println("Control : Stop");
          theBrain.stopTheBrain();
*/

    }

    @Update
    public void update() {
        System.out.println("Control : update");
        stop();
        start();
    }


    @Port(name = "RHMI", method = "getFromHmi")
    //HMI ask us for some data
    public LinkedList<Data> getFromHmi(Object o) {
        System.out.println("Control : HMI data receive : " + o);
        HmiRequest HMIAction = (HmiRequest) o;
        LinkedList<Data> object = null;
        if (HMIAction.getAction() == null) {
            //HMI ask for data
            for (int i = 0; i < HMIAction.getDataTypes().size(); i++) {
                RequestResult result = getData(HMIAction.getBeginDate(), HMIAction.getEndDate(), HMIAction.getLocation(), HMIAction.getDataTypes().get(i));
                if (result.isSuccess()) {
                    object = (LinkedList<Data>) result.getResult();
                }
            }
        } else System.out.println("Control ATTENTION : receive no get from Hmi");
        return object;
    }

    @Port(name = "postFromHmi")
    //HMI ask us for some data
    public void postFromHmi(Object o) {

        //HMI send action
        HmiRequest HMIAction = (HmiRequest) o;
        //create a command

        if (HMIAction.getAction() != null) {
            LinkedList<Action> list = new LinkedList<Action>();
            list.add(HMIAction.getAction());
            Command command = new Command(list, Category.USER, (long) 0, (long) 0);
            //send command
            send2Conflict(command);
            System.out.println("command send to conflict : " + command.getActionList().size());
            send2HMI(command);
        } else System.out.println("Control ATTENTION : receive no command from Hmi");
    }

    //HMI need some data so...
    public void send2HMI(Command command) {
        System.out.println("Control : send2HMI");
        getPortByName("HMI", MessagePort.class).process(command);
    }


    @Port(name = "RConflict")
    //Conflict ask us for some data
    public void receiveConflict(Object o) {
        System.out.println("Control : Conflict data receive : ");
        RequestResult result = (RequestResult) o;
        if (result.isSuccess()) {
            System.out.println("result Success");
            //search command into the list, and send it to HMI
            for (int i = 0; i < commandList.size(); i++) {
                if (result.getResult() == commandList.get(i).getId()) {
                    System.out.println("Control : Command " + commandList.get(i).getId() + " validate and send to HMI");
                    // send2HMI(commandList.get(i));
                    //  sendCommand2DAO(commandList.get(i));
                    commandList.remove(i);
                    System.out.println("Control receive confirmation");
                    break;
                }
            }
        }
        else System.out.println("Control receive NO-confirmation");
    }

    //Send an actions list (= command) to conflict
    public void send2Conflict(Command command) {
        commandList.add(command);
        getPortByName("Conflict", MessagePort.class).process(command);
    }


    //send everything that could have been modified
    public void sendData2DAO(Data data) {
        System.out.println("Control : send2DAO data");
        getPortByName("DAO", IDbService.class).create((data));
    }

    public void sendCommand2DAO(Command command) {
        System.out.println("Control : send2DAO command");
        getPortByName("DAO", IDbService.class).create((command));
    }

    public RequestResult getData(Date begin, Date end, String location, DataType type) {
        LinkedList<Object> params = new LinkedList<Object>();

        params.add(begin);
        params.add(end);
        params.add(location);
        params.add(type);

        RequestResult result = getPortByName("DAO", IDbService.class).get("findByDate", Data.class.getName(), params);
        return result;
    }


    @Port(name = "presenceFromContext")
    //The context ask for a precise variable saved in database (eg : temp sensor from a room )
    public void receiveFromContext(Object o) {
        System.out.println("Control receive context");
        if (o != null) {
            Agenda agenda = (Agenda) o;
            System.out.println("Control : Context data receive : " + agenda);
            //collect list of agenda event
            agendaList = agenda.getEvents();
            agendaChecker = new AgendaChecker(agendaList);
            agendaChecker.addAgendaEventListener(new AgendaCheckerListener() {
                @Override
                public void eventStart() {
                    System.out.println("presence");
                    theBrain.searchRoom("").presence = true;
                }

                @Override
                public void eventStop() {
                    System.out.println("non presence");
                    theBrain.searchRoom("").presence = false;
                }
            });
            agendaChecker.start();
            new Thread() {
                public void run() {
                    while (!isInterrupted()) {
                        try {
                            if (agendaList.getLast().getEnd().before(new Date())) {
                                agendaChecker.setActive(false);
                            }
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            }.start();

        }
    }


    @Port(name = "RSensors")
    public void receiveSensors(Object o) {
        System.out.println("Control : Sensors data receive");
        if (o != null) {
            Data sensor = (Data) o;
            System.out.println("Control : Sensor data in " + sensor.getSensor().getLocation() + " send to theBrain");
            //send new data to the DAO
            sendData2DAO(sensor);

            BrainRoom br = theBrain.searchRoom(sensor.getSensor().getLocation());
            DataType thisDataType = sensor.getSensor().getDataType();
            if (thisDataType.equals(DataType.TEMPERATURE) || thisDataType.equals(DataType.BRIGHTNESS) || thisDataType.equals(DataType.HUMIDITY)) {
                //update room, for fullAuto principally
                br.updateRoom(sensor);
            } else if (thisDataType.equals(DataType.POWER)) {
                Command com = new Command();
                com.setActionList(br.lightControl(sensor.getValue()));
                com.setCategory(Category.USER);
                com.setLock((long) 1);
                com.setTimeOut((long) 1);
                send2Conflict(com);
            } else if (thisDataType.equals(DataType.POWER)) {
                Command com = new Command();
                com.setActionList(br.temperatureControl(sensor.getValue()));
                com.setCategory(Category.USER);
                com.setLock((long) 1);
                com.setTimeOut((long) 1);
                send2Conflict(com);
            } else System.out.println("data type receive by hardware : " + sensor.getSensor().getDataType());
        }
    }
}
