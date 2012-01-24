package fr.esir2011.nsoc;

import java.io.IOException;
import org.restlet.Application;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class ClientAndro extends Application{
	ClientResource cr;
	static Client cl;
	String urlServer = null;
	
	//Constructeur
	public ClientAndro(){
		cl = new Client(new Context(), Protocol.HTTP);
	}
	
	//suppression du client (appui sur bouton deconnexion)
	public void killClient(){
		try {
			cl.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//creation du client (appui sur bouton connexion)
	public void creationClient(String ip){
		urlServer = "http://148.60.83.56:8182";
		try {
			cl.start();
			cr = new ClientResource(urlServer);
			cr.setNext(cl);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	

}

