package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.*;
import esir.dom11.nsoc.service.IDbService;
import esir.dom11.nsoc.service.IServerService;
import esir.dom11.nsoc.service.RequestResult;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import java.util.Date;
import java.util.LinkedList;


@Library(name = "NSOC_2011")
@ComponentType
@Provides({
        @ProvidedPort(name = "RHMI", type = PortType.SERVICE, className = IServerService.class),
        @ProvidedPort(name = "postFromHmi", type = PortType.MESSAGE) ,
        @ProvidedPort(name = "presenceFromContext", type = PortType.MESSAGE) ,
        @ProvidedPort(name = "RConflict", type = PortType.MESSAGE),
        @ProvidedPort(name = "RSensors", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "HMI", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "Context", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "DAO", type = PortType.SERVICE, className = IDbService.class, needCheckDependency = true, optional = true),
        @RequiredPort(name = "Conflict", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "Sensors", type = PortType.MESSAGE, optional = true)
})

public class Control extends AbstractComponentType implements ctrlInterface,IServerService {
    //Attribute
    private TheBrain theBrain;
    private LinkedList<Command> commandList;
    private LinkedList<AgendaEvent> agendaList;
    private AgendaChecker agendaChecker;

    @Start
    public void start() {
        System.out.println("Control : Start");

        agendaList = new LinkedList<AgendaEvent>();
        commandList = new LinkedList<Command>();
        theBrain = new TheBrain();
        theBrain.createRoom("/bat7/salle930/");
    }
    @Stop
    public void stop() {
        System.out.println("Control : Stop");
        theBrain.stopTheBrain();
        commandList = null;
        agendaList = null;
        if(agendaChecker !=null){
            agendaChecker.setActive(false);
        }
    }
    @Update
    public void update() {
        System.out.println("Control : update");
        stop();
        start();
    }


    /****HMI****/
    @Port(name = "RHMI", method = "getFromHmi")
    //HMI service receive HmiRequest to get data and send response
    public LinkedList<Data> getFromHmi(Object o) {
        System.out.println("Control : HMI data receive : "+ o);
        HmiRequest HMIAction = (HmiRequest) o;
        LinkedList<Data> object = null;
        if(HMIAction.getAction() == null){
            //HMI ask for data
            for(int i = 0; i < HMIAction.getDataTypes().size(); i ++){
                RequestResult result = getData(HMIAction.getBeginDate(), HMIAction.getEndDate(),HMIAction.getLocation(),HMIAction.getDataTypes().get(i));
                if (result.isSuccess()) {
                    object = (LinkedList<Data>) result.getResult();
                }
            }
        }
        else System.out.println("Control ATTENTION : receive no get from Hmi");
        System.out.println("Control : data list send to Hmi ");
        return object;
    }
    @Port(name = "postFromHmi")
    //HMI send action
    public void postFromHmi(Object o) {
        HmiRequest HMIAction = (HmiRequest) o;
        if(HMIAction.getAction() != null)  {
            LinkedList<Action> list = new LinkedList<Action>();
            list.add(HMIAction.getAction());
            Command command = new Command(list, Category.USER,(long) 0, (long) 0 ) ;
            //send command
            send2Conflict(command);
            System.out.println("Control : command send to conflict ");
        }
        else System.out.println("Control ATTENTION : receive no command from Hmi");
    }
    //Control send command to Hmi
    public void send2HMI(Command command) {
        System.out.println("Control : send2HMI");
        getPortByName("HMI",MessagePort.class).process(command);
    }

    /****Conflict****/
    @Port(name = "RConflict")
    //Conflict send confirmation of request
    public void receiveConflict(Object o) {
        System.out.println("Control : Conflict data receive : ");
        RequestResult result = (RequestResult) o;
        if(result.isSuccess()){
            System.out.println("result Success");
            //search command into the list, and send it to HMI
            for(int i = 0; i < commandList.size(); i ++){
                if(result.getResult() == commandList.get(i).getId()){
                    System.out.println("Control : Command " + commandList.get(i).getId() + " validate and send to HMI");
                    send2HMI(commandList.get(i));
                    sendCommand2DAO(commandList.get(i));
                    commandList.remove(i);
                    break;
                }
            }
        }
    }
    //Send command to conflict
    public void send2Conflict(Command command) {
        System.out.println("Control : send2Conflict : " + command.getActionList().get(0));
        commandList.add(command);
        getPortByName("Conflict",MessagePort.class).process(command);
    }

    /****DAO****/
	//send data to DAO
	public void sendData2DAO(Data data) {
        System.out.println("Control : send2DAO data");
        getPortByName("DAO", IDbService.class).create((data));
	}
    //send command to DAO
	public void sendCommand2DAO(Command command) {
        System.out.println("Control : send2DAO command");
        for(int i=0; i<command.getActionList().size(); i++){
            getPortByName("DAO", IDbService.class).create(command.getActionList().get(i));
        }
	}
    //ask somme data of DAO
    public RequestResult getData(Date begin, Date end, String location, DataType type){
        LinkedList<Object> params = new LinkedList<Object>();

        params.add(begin);
        params.add(end);
        params.add(location);
        params.add(type);

        RequestResult result = getPortByName("DAO", IDbService.class).get("findByDate",Data.class.getName(),params);
        return result;
    }

    /****Context****/
    @Port(name = "presenceFromContext")
    //Context send LinkedList of Agenda Event correspond to estimate presence of the day
    public void receiveFromContext(Object o) {
        System.out.println("Control receive context");
        if(o != null){
            LinkedList<AgendaEvent> agenda = (LinkedList<AgendaEvent>) o;
            System.out.println("Control : Context data receive ");
            //collect list of agenda event
            agendaList = agenda;
            if(agendaChecker !=null){
                agendaChecker.setActive(false);
            }
            agendaChecker = new AgendaChecker(agendaList);
            agendaChecker.addAgendaEventListener(new AgendaCheckerListener() {
                @Override
                public void eventStart() {
                    System.out.println("presence " + new Date());
                    theBrain.searchRoom("/bat7/salle930/").presence = true;
                }

                @Override
                public void eventStop() {
                    System.out.println("non presence " + new Date());
                    theBrain.searchRoom("/bat7/salle930/").presence = false;
                }
            });
            agendaChecker.start();
            new Thread() {
                public void run() {
                    while (agendaChecker.isActive()){
                        try {
                            if((new Date().getTime() - agendaList.getLast().getEnd().getTime()) > 1000) {
                                agendaChecker.setActive(false);
                                break;
                            }
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            }.start();
        }
    }

    /****Hardware****/
    @Port(name = "RSensors")
    //receive sensor or actuator data
    public void receiveSensors(Object o){
        System.out.println("Control : Sensors data receive");
        if(o != null){
            Data sensor = (Data) o;
            System.out.println("Control : Sensor data in " +sensor.getSensor().getLocation() + " send to theBrain");
            //send new data to the DAO
            sendData2DAO(sensor);

            BrainRoom br = theBrain.searchRoom(sensor.getSensor().getLocation());
            DataType thisDataType = sensor.getSensor().getDataType();
            if(thisDataType.equals(DataType.TEMPERATURE) || thisDataType.equals(DataType.BRIGHTNESS)){
                br.updateRoom(sensor);
            }
            else if(thisDataType.equals(DataType.SWITCH)){
                String inter = sensor.getSensor().getLocation().split("/")[4];
                if(inter.equals("1")){
                    Command com = new Command();
                    com.setActionList(br.lightControl(sensor.getValue()));
                    com.setCategory(Category.USER);
                    com.setLock((long) 1);
                    com.setTimeOut((long) 1);
                    send2Conflict(com);
                }
                else if(inter.equals("2")){
                    Command com = new Command();
                    com.setActionList(br.temperatureControl(sensor.getValue()));
                    com.setCategory(Category.USER);
                    com.setLock((long) 1);
                    com.setTimeOut((long) 1);
                    send2Conflict(com);
                }
            }
            else System.out.println("data type receive by hardware : " + sensor.getSensor().getDataType());
        }
    }
}