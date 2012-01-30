package esir.server;

import esir.dom11.nsoc.model.Data;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


/**
 * Created by IntelliJ IDEA.
 * User: Pierre
 * Date: 07/01/12
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */


public class ServerManager extends ServerResource{
    private Component component;

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
        // Collect all the important information of the url sent for a Get Request
        String url =  getReference().getRemainingPart();
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
            String building;
            Data data;
            Ihm2Ctrl ic = new Ihm2Ctrl();
            LinkedList<DataType> datatypes = new LinkedList<DataType>();
            ServerComponent sc = new ServerComponent();


            // 2. get all current data
            // client ip : http://@IP:port/all/building/room/
            // we set the kind of DataType ine the server
            if(parameters[0].equals("all")){
                building = parameters[1] + "/" + parameters[2];
                ic.setAction("get");
                ic.setChoice(parameters[0]);
                ic.setLocation(building);

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
                building = parameters[1] + "/" + parameters[2];
                ic.setAction("get");
                ic.setChoice(parameters[0]);
                ic.setLocation(building);
                //pb in the kind of datataypes here
                //ic.setDataTypes(ic.add(parameters[3]));
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
