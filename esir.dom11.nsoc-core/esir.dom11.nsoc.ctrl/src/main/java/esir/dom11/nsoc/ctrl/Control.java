package esir.dom11.nsoc.ctrl;

import esir.dom11.nsoc.model.*;
import esir.dom11.nsoc.model.device.Device;
import esir.dom11.nsoc.model.device.Sensor;
import esir.dom11.nsoc.service.RequestResult;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;


@Library(name = "NSOC_2011")
@ComponentType
@Provides({
        @ProvidedPort(name = "RHMI", type = PortType.MESSAGE) ,
        @ProvidedPort(name = "RContext", type = PortType.MESSAGE) ,
        @ProvidedPort(name = "RConflict", type = PortType.MESSAGE),
        @ProvidedPort(name = "RSensors", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "HMI", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "Context", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "DAO", type = PortType.MESSAGE, optional = true) ,
        @RequiredPort(name = "Conflict", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "Sensors", type = PortType.MESSAGE, optional = true)
})

public class Control extends AbstractComponentType implements ctrlInterface {
    private TheBrain theBrain;
    private LinkedList<Command> commandList;
    
    @Start
    public void start() {
        System.out.println("Control : Start");


        HmiRequest ic = new HmiRequest();
        LinkedList<DataType> datatypes = new LinkedList<DataType>();

        //create the object to send to the Controller
        ic.setAction(HmiRequest.HmiRequestAction.GET);
        ic.setLocation("b7-s930");
        // add all the dataTypes in the dataTypes list
        datatypes.add(DataType.TEMPERATURE);
        datatypes.add(DataType.BRIGHTNESS);
        datatypes.add(DataType.HUMIDITY);
        datatypes.add(DataType.POWER);
        ic.setDataTypes(datatypes);

        /*    Sensor dev = new Sensor(DataType.TEMPERATURE, "/B7/930/");
          Date date = new Date();
          Sensor dev2 = new Sensor(DataType.TEMPERATURE, "/B7/930/");
          Date date2 = new Date();

          Data data1 = new Data(dev, (double) 10, date) ;
          Data data2 = new Data(dev2, (double) 13, date2);

          LinkedList<Data> list = new LinkedList<Data>() ;
          list.add(data1);
          list.add(data2);
          System.out.println("send list to HMI");
          send2HMI(list);
        */

        sendMessage(ic);
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



	//HMI need some data so...
	public void send2HMI(Command command) {
        System.out.println("Control : send2HMI");
        getPortByName("HMI",MessagePort.class).process(command);
    }
    public void send2HMI(LinkedList<Data> dataList) {
        System.out.println("Control : send2HMI");
        getPortByName("HMI",MessagePort.class).process(dataList);
    }

	//send everything that could have been modified
	public void send2DAO(Data data) {
        System.out.println("Control : send2DAO data");
        getPortByName("DAO",MessagePort.class).process(data);
	}
	public void send2DAO(Command command) {
        System.out.println("Control : send2DAO command");
        getPortByName("DAO",MessagePort.class).process(command);
	}


	//Send an actions list (= command) to conflict 
	public void send2Conflict(Command command) {
        System.out.println("Control : send2Conflict");
        commandList.add(command);
        getPortByName("Conflict",MessagePort.class).process(command);
	}

    //send request to receive value of sensor
    public void send2Sensors(DataType dataType) {
        System.out.println("Control : send2Sensors");
        getPortByName("Sensors",MessagePort.class).process(dataType);
    }

    @Port(name = "RHMI")
	//HMI ask us for some data
	public void receiveHMI(Object o) {
		System.out.println("Control : HMI data receive : ");
        HmiRequest HMIAction = (HmiRequest) o;
        
        if(HMIAction.getAction().equals(HmiRequest.HmiRestRequest.GET)){
            //HMI ask for data
            //send request to TheBrain
    //        theBrain.sendInfoTo(HMIAction.getLocation(), HMIAction.getDataTypes(), HMIAction.getBeginDate(), HMIAction.getEndDate());

            System.out.println("receive GET");

            Sensor dev = new Sensor(DataType.TEMPERATURE, "/B7/930/");
            Date date = new Date();
            Sensor dev2 = new Sensor(DataType.TEMPERATURE, "/B7/930/");
            Date date2 = new Date();

            Data data1 = new Data(dev, (double) 10, date) ;
            Data data2 = new Data(dev2, (double) 13, date2);
            
            LinkedList<Data> list = new LinkedList<Data>() ;
            list.add(data1);
            list.add(data2);
            System.out.println("send list to HMI");
            send2HMI(list);
        }
        else if(HMIAction.getAction().equals(HmiRequest.HmiRestRequest.POST)){
            //HMI send action
            LinkedList<Action> temp = new LinkedList<Action>();

        //    temp.add();
            Category cat = Category.USER;
            long lock = 1;
            long timeOut = 1;
            //create command associate
            Command HMICommand = new Command(temp, cat, lock, timeOut);


            //        theBrain.sendCommandTo("HMI",HMICommand);
//        System.out.println("Control : HMI command send to theBrain");
            //test
//        send2Conflict(HMICommand);


        }
        else{
            System.out.println("bad action!");
        }
	}

	@Port(name = "RConflict")
	//Conflict ask us for some data
	public void receiveConflict(Object o) {
        System.out.println("Control : Conflict data receive : ");
        RequestResult result = (RequestResult) o;
        if(result.isSuccess()){
            UUID idCommand = (UUID) result.getResult();
            //search command into the list, and send it to HMI
            for(int i = 0; i < commandList.size(); i ++){
               if(result.getResult() == commandList.get(i).getId()){
                   System.out.println("Control : Command " + commandList.get(i).getId() + " validate and send to HMI");
                   send2HMI(commandList.get(i));
                   commandList.remove(i);
                   break;
               }
            }
        }
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
            System.out.println("Control : Sensor data in " +sensor.getDevice().getLocation() + " send to theBrain");
        }
    }



    public void sendMessage(HmiRequest message){
        MessagePort prodPort = getPortByName("HMI", MessagePort.class);
        if(message != null){
            System.out.println("test");
            prodPort.process(message);
        }
    }
}