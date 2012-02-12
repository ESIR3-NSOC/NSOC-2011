package rest;

import java.io.IOException;

import org.restlet.resource.ResourceException;

public class testConnexion {

    public static void main(String[] args) {
	ConnexionManager connexion = new ConnexionManager();
	String datatype = "light";
	String building = "bat7";
	String room = "salle930";
	String idActuator = "da5ca0b3-3139-48d1-baed-128cb3869568";
	String value = "0";
	String idAction = "8e207b0a-c052-4e55-8aef-840eb73fe3eda";
	boolean command = connexion.sendRequest(idAction, idActuator, datatype, building, room, value);  
	System.out.println(command);
    }
}
