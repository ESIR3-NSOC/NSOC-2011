package fr.esir2011.nsoc;

import java.io.IOException;
import org.restlet.Application;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import android.widget.*;
import android.view.View;


public class ClientAndro extends Application{
	
	MainActivity activity;
	ClientResource cr;
	static Client cl;
	String urlServer = null;
	
	//Constructeur
	public ClientAndro( MainActivity main_activity){
		activity = main_activity;
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
		
		
		urlServer = "ip";
		try {
			
			CheckBox checkConnect;
			checkConnect=(CheckBox)activity.findViewById(R.id.checkConnect);
			
			cl.start();
			cr = new ClientResource(urlServer);
			cr.setNext(cl);
			
			if (cr.get().equals("Connexion Client/Serveur etablie")){
				checkConnect.setChecked(true);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	

}

