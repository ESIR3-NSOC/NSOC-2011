package esir.dom11.nsoc.server;

import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.HmiRequest;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class ServerManager extends ServerResource{
    private Component _component;
    private ServerComponent _sc;
    private HmiRequest _ic;
    private LinkedList<DataType>_datatypes;

    public ServerManager(ServerComponent sc){
        _sc = sc;
    }

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

            // Now, let's start the _component!
            // Note that the HTTP server connector is also automatically started.
            _component.start();

            //we will fill the data in the LocalStorage
            _ic = new HmiRequest();
            _datatypes = new LinkedList<DataType>();

            //create the object to send to the Controller
            _ic.setAction(HmiRequest.HmiRequestAction.GET);
            _ic.setLocation("b7-s930");
            // add all the dataTypes in the dataTypes list
            _datatypes.add(DataType.TEMPERATURE);
            _datatypes.add(DataType.BRIGHTNESS);
            _datatypes.add(DataType.HUMIDITY);
            _datatypes.add(DataType.POWER);
            _ic.setDataTypes(_datatypes);

            _sc.sendMessage(_ic);

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
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return addr.getHostAddress().toString();
	}

    @Get
    public Object receiveGetRequest(){
        System.out.println("Get request received!");

        // Collect all the important information of the url sent for a Get Request
        String url =  getReference().getRemainingPart();

        //the minimal lenght is 1 if there are no value.
        //the last index is parameters.length-1 with an empty value
        String[] parameters = url.split("/");

        // There are 3 kind of GET requests
        // 1. connection test between client and server
        // 2. get all current data
        // 3. get details for a dataType

        System.out.println("length= "+parameters.length);
        // 1. connection test between client and server
        // client ip : http://@IP:port
        if(parameters.length == 1){
            return "connected";
        }
        else{
            String location;
            location = parameters[1] + "-" + parameters[2];

            // 2. get all current data
            // client ip : http://@IP:port/building/room/
            // we set the kind of DataType ine the server
            if(parameters.length == 3){
                _ic.setAction(HmiRequest.HmiRequestAction.GET);
                _ic.setLocation(location);

                // add all the dataTypes in the dataTypes list
                _datatypes.add(DataType.TEMPERATURE);
                _datatypes.add(DataType.BRIGHTNESS);
                _datatypes.add(DataType.HUMIDITY);
                _datatypes.add(DataType.POWER);

                _ic.setDataTypes(_datatypes);
            }

            // 3. get detail for a dataType
            // client ip : http://@IP:port/building/room/dataType/beginDate/endDate/
            else {
                _ic.setAction(HmiRequest.HmiRequestAction.GET);
                _ic.setLocation(location);

                // create the List of all DataTypes (here, we have only one)
                _datatypes.add(DataType.valueOf(parameters[3].toUpperCase()));

                _ic.setDataTypes(_datatypes);

                try{
                    DateFormat format = new SimpleDateFormat("jj-mm-yyyy");

                    _ic.setBeginDate((Date) format.parse(parameters[4]));
                    _ic.setEndDate((Date) format.parse(parameters[5]));
                }
                catch (Exception e){
                    System.out.println("Exception :"+e);
                }
            }

            // send the HmiRequest object to the Controller within the requires port
            _sc.sendMessage(_ic);

            return LocalStorage.getLocalStorageObject().getAllData();
        }
    }

    @Post
    public void receivePostRequest(){}

    @Put
    public void receivePutRequest(){}
}
