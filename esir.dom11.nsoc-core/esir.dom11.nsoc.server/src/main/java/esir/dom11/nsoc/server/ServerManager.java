package esir.dom11.nsoc.server;

import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.DataType;
import esir.dom11.nsoc.model.HmiRequest;
import org.restlet.Component;
import org.restlet.Server;
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

public class ServerManager extends ServerResource {
    private Component _component;
    private ServerComponent _sc;
    private LinkedList<DataType> _datatypes;

    public void init(ServerComponent sc){
        _sc = sc;
        _datatypes = new LinkedList<DataType>();
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

        System.out.println("Component : " + _component);
        try{
            System.out.println("/** Server launched **/");
            System.out.println("/**");
            System.out.println(" * Server created : http://"+this.getIpServer()+":"+port);
            System.out.println(" */");

            _component.start();

            // add all the dataTypes in the dataTypes list
            _datatypes.add(DataType.TEMPERATURE);
            _datatypes.add(DataType.BRIGHTNESS);
            _datatypes.add(DataType.HUMIDITY);
            _datatypes.add(DataType.POWER);

            HmiRequest hr = new HmiRequest("b7-s930", _datatypes);
            _sc = new ServerComponent();
            _sc.sendMessage(hr);

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

        //the minimal lenght is 1 if there are no value.
        //the last index is parameters.length-1 with an empty value
        String[] parameters = url.split("/");

        // There are 3 kind of GET requests
        // 1. connection test between client and server
        // 2. get all current data
        // 3. get details for a dataType

        // 1. connection test between client and server
        // client ip : http://@IP:port
        System.out.println("parameters.length " +parameters.length);
        for(int i=0; i<parameters.length; i++){
            System.out.println("parameters : "+parameters[i]);
        }
        if(parameters.length <= 1){
            return "connected";
        }
        else{
            HmiRequest hr;
            Date beginDate = new Date();
            Date endDate = new Date();
            String location = parameters[1] + "-" + parameters[2];

            // 2. get all current data
            // client ip : http://@IP:port/building/room/
            // we set the kind of DataType ine the server
            if(parameters.length == 3){
                _datatypes.clear();
                // add all the dataTypes in the dataTypes list
                _datatypes.add(DataType.TEMPERATURE);
                _datatypes.add(DataType.BRIGHTNESS);
                _datatypes.add(DataType.HUMIDITY);
                _datatypes.add(DataType.POWER);

                hr = new HmiRequest(location, _datatypes);
            }

            // 3. get detail for a dataType
            // client ip : http://@IP:port/building/room/dataType/beginDate/endDate/
            else if(parameters.length == 5){
                // create the List of all DataTypes (here, we have only one)
                _datatypes.clear();
                _datatypes.add(DataType.valueOf(parameters[3].toUpperCase()));

                try{
                    DateFormat format = new SimpleDateFormat("jj-mm-yyyy");
                    beginDate = format.parse(parameters[4]);
                    endDate = format.parse(parameters[5]);
                }
                catch (Exception e){
                    System.out.println("Exception :"+e);
                }

                 hr = new HmiRequest(location, _datatypes, beginDate, endDate);

            }
            else { return "Your url is not correct"; }

            // send the HmiRequest object to the Controller within the requires port
            _sc.sendMessage(hr);

            return LocalStorage.getLocalStorageObject().getAllData();
        }
    }

    @Post
    public void receivePostRequest(Form form){
        Action action = new Action(
                UUID.fromString(form.getValues("idAction").toString()),
                UUID.fromString(form.getValues("idAction").toString()),
                Double.parseDouble(form.getValues("value").toString())
        );

        HmiRequest hr = new HmiRequest(form.getValues("location"), action);

        _sc.sendMessage(hr);

    }

    @Put
    public void receivePutRequest(){}

}
