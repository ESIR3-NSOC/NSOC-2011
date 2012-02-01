package esir.server;

import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.Ihm2Ctrl;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class ServerManager extends ServerResource{
    private Component component;
    private ServerComponent sc = new ServerComponent();
    private Ihm2Ctrl ic;
    private LinkedList<DataType> datatypes;

    /*
     * Start the REST server for the IHM
     */
    public Boolean startServer(Integer port){
        // Create a new Restlet component and add a HTTP server connector to it
        component = new Component();
        component.getServers().add(Protocol.HTTP, 8182);

        // Then attach it to the local host
        component.getDefaultHost().attach("/", ServerManager.class);

        try{
            System.out.println("/** Server launched **/");
            System.out.println("Server created : http://"+this.getIpServer()+":"+port);

            // Now, let's start the component!
            // Note that the HTTP server connector is also automatically started.
            component.start();

            //we will fill the data in the LocalStorage
            ic = new Ihm2Ctrl();
            datatypes = new LinkedList<DataType>();

            //create the object to send to the Controller
            ic.setAction(Ihm2Ctrl.IhmAction.GET);
            ic.setLocation("b7-s930");
            // add all the dataTypes in the dataTypes list
            datatypes.add(DataType.TEMPERATURE);
            datatypes.add(DataType.BRIGHTNESS);
            datatypes.add(DataType.HUMIDITY);
            datatypes.add(DataType.POWER);
            ic.setDataTypes(datatypes);
            sc.sendMessage(ic);

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
            component.stop();
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

        System.out.println(url);

        String[] parameters = url.split("/");

        // There are 3 kind of GET requests
        // 1. connection test between client and server
        // 2. get all current data
        // 3. get detail for a dataType


        // 1. connection test between client and server
        // client ip : http://@IP:port
        if(parameters.length == 0){
            return "connected";
        }
        else{
            String location;
            location = parameters[1] + "-" + parameters[2];

            // 2. get all current data
            // client ip : http://@IP:port/all/building/room/
            // we set the kind of DataType ine the server
            if(parameters[0].equals("all")){
                ic.setAction(Ihm2Ctrl.IhmAction.GET);
                ic.setLocation(location);

                // add all the dataTypes in the dataTypes list
                datatypes.add(DataType.TEMPERATURE);
                datatypes.add(DataType.BRIGHTNESS);
                datatypes.add(DataType.HUMIDITY);
                datatypes.add(DataType.POWER);

                ic.setDataTypes(datatypes);
            }

            // 3. get detail for a dataType
            // client ip : http://@IP:port/detail/building/room/dataType/beginDate/endDate/
            else if(parameters[0].equals("detail")){
                ic.setAction(Ihm2Ctrl.IhmAction.GET);
                ic.setLocation(location);

                // create the List of all DataTypes (here, we have only one)
                datatypes.add(DataType.valueOf(parameters[3].toUpperCase()));

                ic.setDataTypes(datatypes);

                try{
                    DateFormat format = new SimpleDateFormat("jj-mm-yyyy");

                    ic.setBeginDate((Date) format.parse(parameters[4]));
                    ic.setEndDate((Date) format.parse(parameters[5]));
                }
                catch (Exception e){
                    System.out.println("Exception :"+e);
                }
            }

            // send the Ihm2Ctrl object to the Controller within the requires port
            sc.sendMessage(ic);

            return LocalStorage.getLocalStorageObject().getAllData();
        }
    }

    @Post
    public void receivePostRequest(){}

    @Put
    public void receivePutRequest(){}
}
