package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.*;
import esir.dom11.nsoc.model.device.Actuator;
import esir.dom11.nsoc.model.device.Sensor;
import esir.dom11.nsoc.service.IDbService;
import esir.dom11.nsoc.service.RequestResult;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import java.util.Date;
import java.util.LinkedList;

@Library(name = "NSOC_2011")
@ComponentType
@Provides({
        @ProvidedPort(name = "RHMI", type = PortType.SERVICE, className = Control.class),
        @ProvidedPort(name = "RContext", type = PortType.MESSAGE) ,
        @ProvidedPort(name = "RConflict", type = PortType.MESSAGE),
        @ProvidedPort(name = "RSensors", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "Context", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "DAO", type = PortType.SERVICE, className = IDbService.class, needCheckDependency = true),
        @RequiredPort(name = "Conflict", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "Sensors", type = PortType.MESSAGE, optional = true)
})

public class Control extends AbstractComponentType implements ctrlInterface {
    private TheBrain theBrain;
    private LinkedList<Command> commandList;

    @Start
    public void start() {
        System.out.println("Control : Start");

        commandList = new LinkedList<Command>();


   /*     LinkedList<Action> list = new LinkedList<Action>();
        Actuator actuator = new Actuator(DataType.TEMPERATURE, "bat7/s930");
        Action action = new Action(actuator, (double) 10);

        list.add(action);
        Command command = new Command(list, Category.USER,(long) 0, (long) 0 ) ;
        //send command
        send2Conflict(command);
     */


       // HmiRequest ic = new HmiRequest();
       // LinkedList<DataType> datatypes = new LinkedList<DataType>();

   /* test      list2 = new LinkedList<Data>() ;
          list2.add(data1);
          list2.add(data2);
    */
/*        //Brain starting
        theBrain = new TheBrain();
        theBrain.createRoom("B", "930");
*/    }

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
    public Object getFromHmi(Object o, Object id) {
        System.out.println("Control : HMI data receive : "+ o);
        HmiRequest HMIAction = (HmiRequest) o;
        Object object = null;
        //HMI ask for data
        for(int i = 0; i < HMIAction.getDataTypes().size(); i ++){
            RequestResult result = getData(HMIAction.getBeginDate(), HMIAction.getEndDate(),HMIAction.getLocation(),HMIAction.getDataTypes().get(i));
            if (result.isSuccess()) {
                object = (LinkedList<Data>) result.getResult();
            }
        }
        return object;
    }
    @Port(name = "postFromHmi")
    //HMI ask us for some data
    public void postFromHmi(Object o) {
        //HMI send action
        HmiRequest HMIAction = (HmiRequest) o;
        //create a command
        LinkedList<Action> list = new LinkedList<Action>();
        list.add(HMIAction.getAction());
        Command command = new Command(list, Category.USER,(long) 0, (long) 0 ) ;
        //send command
        send2Conflict(command);
    }
    //HMI need some data so...
    public void send2HMI(Command command) {
        System.out.println("Control : send2HMI");
        getPortByName("HMI",MessagePort.class).process(command);
    }



    @Port(name = "RConflict")
    //Conflict ask us for some data
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
    //Send an actions list (= command) to conflict
    public void send2Conflict(Command command) {
        System.out.println("Control : send2Conflict : " + command.getActionList().get(0));
        commandList.add(command);
        getPortByName("Conflict",MessagePort.class).process(command);
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
    public RequestResult getData(Date begin, Date end, String location, DataType type){
        LinkedList<Object> params = new LinkedList<Object>();

        params.add(begin);
        params.add(end);
        params.add(location);
        params.add(type);

        RequestResult result = getPortByName("DAO", IDbService.class).get("findByDate",Data.class.getName(),params);
        return result;
    }



    @Port(name = "RContext")
    //The context ask for a precise variable saved in database (eg : temp sensor from a room )
    public void receiveFromContext(Object o) {
        System.out.println("Control : Context data receive : ");
    }



    @Port(name = "RSensors")
    public void receiveSensors(Object o){
        System.out.println("Control : Sensors data receive");
        if(o != null){
            Data sensor = (Data) o;
       //     theBrain.sendInfoTo(sensor.getDevice().getLocation(), sensor);
            System.out.println("Control : Sensor data in " +sensor.getSensor().getLocation() + " send to theBrain");
            //send new data to the DAO
            sendData2DAO(sensor);
        }
    }
    //send request to receive value of sensor
    public void send2Sensors(DataType dataType) {
        System.out.println("Control : send2Sensors");
        getPortByName("Sensors",MessagePort.class).process(dataType);
    }
}