package rest;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

/**
 * Client -> Server Rest
 * @author Laurent Cabon
 *
 */

public class ConnexionManager {

	String IP;
	String Port;
	
	/**
	 * Getters/Setters
	 */
	
	// get the IP address
	String ip(){
		if(IP == null){
			return "192.168.1.1";
		}
		else {
			return IP;
		}
	}
	
	// get the port
	String port(){
		if(Port == null){
			return "8182";
		}
		else {
			return Port;
		}
	}
	
	// set the ip address
	void setIP(String ip){
		this.IP = ip;
	}
	
	// set the port 
	void setPort(String port){
		
	}
	
	/**
	 * Send Request to the server
	 */
	public boolean sendRequest(String idAction, String idActuator, String datatype, String building, String room, String value){
	 	
		// retrieve the server ip address
		ClientResource itemsResource = new ClientResource(
                "http://"+this.ip()+":"+this.port());
        ClientResource itemResource = null;  
        // url
        System.out.println("url="+ "http://"+this.ip()+":"+this.port());

        if(itemsResource.getStatus().isError()){
        	System.out.println("Link to the server broken");
        	return false;
        }
		else {
		// create the request
		Form webForm = new Form();
		webForm.add("idAction", idAction);
		webForm.add("idActuator", idActuator);
		webForm.add("datatype", datatype);
		webForm.add("building", building);
		webForm.add("room", room);
		webForm.add("value", value);

	    // send post request
        Representation r = itemsResource.post(webForm);  
        itemResource = new ClientResource(r.getLocationRef()); 
        System.out.println("Command sent !");
        return true;
        }       	
}


public static void main(String[] args) throws IOException,  
ResourceException {
	new ConnexionManager();
}
	
}
