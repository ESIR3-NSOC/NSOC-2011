package esir.dom11.nsoc.server;

import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.HmiRequest;
import esir.dom11.nsoc.model.device.Actuator;
import org.restlet.Component;
import org.restlet.data.Form;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class ServerManager extends ServerResource{
    private Component _component;

    /*
     * Start the REST server for the IHM
     */
    public Boolean startServer(Integer port){
        // Create a new Restlet _component and add a HTTP server connector to it
        _component = new Component();
        _component.getServers().add(Protocol.HTTP, 8182);

        // Then attach it to the local host
        _component.getDefaultHost().attach("/", ServerManager.class);

        try{
            System.out.println("/** Server launched **/");
            System.out.println("/**");
            System.out.println(" * Server created : http://"+this.getIpServer()+":"+port);
            System.out.println(" */");

            _component.start();

            LinkedList<DataType> datatypes = new LinkedList<DataType>();

            // add all the dataTypes in the dataTypes list
            datatypes.add(DataType.TEMPERATURE);
            datatypes.add(DataType.BRIGHTNESS);
            datatypes.add(DataType.HUMIDITY);
            datatypes.add(DataType.POWER);

            HmiRequest hr = new HmiRequest("b7-s930", datatypes);
            ServerComponent sc = LocalStorage.getLocalStorageObject().getServerComponent();
            sc.sendMessage(hr);

            System.out.println("Object sent!");

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Stop the REST server for the IHM
     */
    public Boolean stopServer(){
        try{
            System.out.println("/** Server stopped **/");
            _component.stop();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /*
     * @return String the IP address of the computer
     */
    public String getIpServer() {
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return address.getHostAddress().toString();
	}

    @Get
    public Object receiveGetRequest(){
        System.out.println("Get request received!");

        // Collect all the important information of the url sent for a Get Request
        String url =  getReference().getRemainingPart();

        //the minimal length is 1 if there are no value.
        //the last index is parameters.length-1 with an empty value
        String[] parameters = url.split("/");

        System.out.println("parameters.length: "+parameters.length);
        for(int i=0; i<parameters.length; i++){
            System.out.println("parameters : "+parameters[i]);
        }

        // There are 3 kind of GET requests
        // 1. connection test between client and server
        // 2. get all current data
        // 3. get details for a dataType

        // 1. connection test between client and server
        // client ip : http://@IP:port

        if(parameters.length == 1){
            return "connected";
        }
        else{
            HmiRequest hr;
            Date beginDate = new Date();
            Date endDate = new Date();
            String location = parameters[0] + "-" + parameters[1];

            // 2. get all current data
            // client ip : http://@IP:port/building/room/
            // we set the kind of DataType ine the server
            if(parameters.length == 2){
                LinkedList<DataType> datatypes = new LinkedList<DataType>();
                // add all the dataTypes in the dataTypes list
                datatypes.add(DataType.TEMPERATURE);
                datatypes.add(DataType.BRIGHTNESS);
                datatypes.add(DataType.HUMIDITY);
                datatypes.add(DataType.POWER);

                hr = new HmiRequest(location, datatypes);

                ServerComponent sc = LocalStorage.getLocalStorageObject().getServerComponent();
                sc.sendMessage(hr);
            }

            // 3. get detail for a dataType
            // client ip : http://@IP:port/building/room/dataType/beginDate/endDate/
            else if(parameters.length == 5){
                // create the List of all DataTypes (here, we have only one)
                LinkedList<DataType> datatypes = new LinkedList<DataType>();
                datatypes.add(DataType.valueOf(parameters[2].toUpperCase()));

                try{
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    beginDate = format.parse(parameters[3]);
                    endDate = format.parse(parameters[4]);
                }
                catch (Exception e){
                    System.out.println("Exception :"+e);
                }

                hr = new HmiRequest(location, datatypes, beginDate, endDate);
                // send the HmiRequest object to the Controller within the requires port
                ServerComponent sc = LocalStorage.getLocalStorageObject().getServerComponent();
                sc.sendMessage(hr);
            }
            else { return "Your url is not correct"; }

            ServerComponent sc = LocalStorage.getLocalStorageObject().getServerComponent();

            LinkedList<Data> result = sc.sendGetRequest(hr);
            if(result == null){
                return "No data provided";
            }

            // we have to send serialized Data
            LinkedList<String> sendData = new LinkedList<String>();
            for(int i=0; i< result.size(); i++){
                sendData.add(result.get(i).serialized());
            }

            return sendData;
        }
    }

    @Post
    public void receivePostRequest(Form form){
        /*
         *  Information to send a POST request
         *  DataType datatype
         *  String building
         *  String room
         *  String actuator
         *  Double value
         */
        System.out.println("Post command received!");
        System.out.println("datatype: "+form.getValues("datatype") +" \n"+
                           "building: "+form.getValues("building") +" \n"+
                           "room: "+form.getValues("room") + " \n" +
                           "actuator: "+form.getValues("actuator")+" \n"+
                           "value: "+form.getValues("value")  +" \n"
        );


        String location = form.getValues("building")+"/"+form.getValues("room")+ "/"+form.getValues("actuator");

        Actuator actuator = new Actuator(
                DataType.valueOf(form.getValues("datatype").toUpperCase()),
                location
        );

        Action action = new Action(
                actuator,
                form.getValues("value")
        );

        HmiRequest hr = new HmiRequest(location, action);

        ServerComponent sc = LocalStorage.getLocalStorageObject().getServerComponent();
        sc.sendMessage(hr);
    }

    @Put
    public void receivePutRequest(){}

}
