package fr.esir2011.nsoc;



import java.io.IOException;

import org.restlet.Client;
import org.restlet.data.Form;
import org.restlet.representation.Representation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.graphics.drawable.*;


public class Interface_salle extends Activity implements View.OnClickListener{
	ImageView lampe;
	ImageView haut_volet1;
	ImageView haut_volet2;
	ImageView haut_volet3;
	ImageView bas_volet1;
	ImageView bas_volet2;
	ImageView bas_volet3;
	
	String etat_lampe = "off";
	String etat_volet1 = "open";
	String etat_volet2 = "open";
	String etat_volet3 = "open";
	
	MainActivity activity;
	private String full_address;
	
	ClientAndro client = new ClientAndro(activity);
	
	
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.main_interface_salle);
		 
		 //Récupération de l'adresse données dans le MainActivity
		 Bundle infoAddress = this.getIntent().getExtras();
		 //Conversion de la donnée en String
		 full_address = infoAddress.getString("adress_serveur")+"/bat7/salle930";
		 
		 client.creationClient(full_address);
		 Representation data = client.cr.get();
		 try {
			System.out.println("donnees reçues:"+data.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 lampe=(ImageView)findViewById(R.id.lampe1);
		 lampe.setOnClickListener(this);
		 haut_volet1=(ImageView)findViewById(R.id.haut_volet1);
		 haut_volet1.setOnClickListener(this);
		 haut_volet2=(ImageView)findViewById(R.id.haut_volet2);
		 haut_volet2.setOnClickListener(this);
		 haut_volet3=(ImageView)findViewById(R.id.haut_volet3);
		 haut_volet3.setOnClickListener(this);
		 bas_volet1=(ImageView)findViewById(R.id.bas_volet1);
		 bas_volet1.setOnClickListener(this);
		 bas_volet2=(ImageView)findViewById(R.id.bas_volet2);
		 bas_volet2.setOnClickListener(this);
		 bas_volet3=(ImageView)findViewById(R.id.bas_volet3);
		 bas_volet3.setOnClickListener(this);
	 }
		 
		 
		 
		 
		 
		 
		 
			
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				
				//Si le bouton appuyé est la lampe
				if (v == lampe){
					
					//Si son état est éteint
					if(etat_lampe.equals("off")){
						
					changeEtatLampe();
					etat_lampe = "on";
					
			        System.out.println("client batiment:"+client.getStatusService());
			        
			        //Instanciation de la donnée à envoyer au serveur
			        Form form = new Form();
			        //Remplissage de la form en fonction de ce que doit recevoir le serveur
			        form.add("datatype","lamp");
			        form.add("building","bat7");
			        form.add("room","salle930");
			        form.add("actuator","lamp/0");
			        form.add("value","ON");
			        
			        client.cr.post(form);
			        System.out.println("Ordre effectué");
			       
					}
					//Si l'état est "On"
					else {
						
					changeEtatLampe();
					etat_lampe = "off";
					
					 Form form = new Form();
					 form.add("datatype","lamp");
				     form.add("building","bat7");
				     form.add("room","salle930");
				     form.add("actuator","lamp/0");
				     form.add("value","OFF");
					client.cr.post(form);
					
					 }
					
				} 
				
				else if (v==haut_volet1){
					if (etat_volet1.equals("open")){
						Toast.makeText(this, "Le volet est déja ouvert", Toast.LENGTH_LONG).show();
					}
					else {
						 Form form = new Form();
						 form.add("datatype","shutter");
					     form.add("building","bat7");
					     form.add("room","salle930");
					     form.add("actuator","shutter/1");
					     form.add("value","OPEN");
						client.cr.post(form);
						etat_volet1 = "open";
						
						Toast.makeText(this, "Le volet n°1 s'ouvre", Toast.LENGTH_LONG).show();
					}
					
					
				}
				else if (v==haut_volet2){
					if (etat_volet2.equals("open")){
						Toast.makeText(this, "Le volet est déja ouvert", Toast.LENGTH_LONG).show();
					}
					else {
						 Form form = new Form();
						 form.add("datatype","shutter");
					     form.add("building","bat7");
					     form.add("room","salle930");
					     form.add("actuator","shutter/2");
					     form.add("value","OPEN");
						client.cr.post(form);
						etat_volet2 = "open";
						
						Toast.makeText(this, "Le volet n°2 s'ouvre", Toast.LENGTH_LONG).show();
					}
					
					
				}
				else if (v==haut_volet3){
					if (etat_volet3.equals("open")){
						Toast.makeText(this, "Le volet est déja ouvert", Toast.LENGTH_LONG).show();
					}
					else {
						 Form form = new Form();
						 form.add("datatype","shutter");
					     form.add("building","bat7");
					     form.add("room","salle930");
					     form.add("actuator","shutter/3");
					     form.add("value","OPEN");
						client.cr.post(form);
						etat_volet3 = "open";
						
						Toast.makeText(this, "Le volet n°3 s'ouvre", Toast.LENGTH_LONG).show();
					}
	
	
				}
				else if (v==bas_volet1){
					System.out.println("boucle bas volet 1");
					if (etat_volet1.equals("close")){
						Toast.makeText(this, "Le volet est déja fermé", Toast.LENGTH_LONG).show();
					}
					else {
						 Form form = new Form();
						 form.add("datatype","shutter");
					     form.add("building","bat7");
					     form.add("room","salle930");
					     form.add("actuator","shutter/1");
					     form.add("value","CLOSE");
						client.cr.post(form);
						etat_volet1 = "close";
						
						Toast.makeText(this, "Le volet n°1 se ferme", Toast.LENGTH_LONG).show();
					}
	
	
				}
				else if (v==bas_volet2){
					if (etat_volet2.equals("close")){
						Toast.makeText(this, "Le volet est déja fermé", Toast.LENGTH_LONG).show();
					}
					else {
						 Form form = new Form();
						 form.add("datatype","shutter");
					     form.add("building","bat7");
					     form.add("room","salle930");
					     form.add("actuator","shutter/3");
					     form.add("value","CLOSE");
						client.cr.post(form);
						etat_volet2 = "close";
						
						Toast.makeText(this, "Le volet n°2 se ferme", Toast.LENGTH_LONG).show();
					}
	
	
				}
				else if (v==bas_volet3){
					if (etat_volet3.equals("close")){
						Toast.makeText(this, "Le volet est déja fermé", Toast.LENGTH_LONG).show();
					}
					else {
						 Form form = new Form();
						 form.add("datatype","shutter");
					     form.add("building","bat7");
					     form.add("room","salle930");
					     form.add("actuator","shutter/3");
					     form.add("value","CLOSE");
						client.cr.post(form);
						etat_volet3 = "close";
						
						Toast.makeText(this, "Le volet n°3 se ferme", Toast.LENGTH_LONG).show();
					}
	
	
				}

				
			}
		
		 
		
		 	 
	 
	 
	 
	 	/*
		 * Méthode permettant d'inverser l'image de la lampe "on-off"
		 */
	 private void changeEtatLampe() {
		 
		 ImageView lampe = (ImageView)findViewById(R.id.lampe1);
		 Drawable nouvelle_Image;
		 //String etat_lampe;
		 
		 if (etat_lampe.equals("on")){
			 nouvelle_Image= getResources().getDrawable(R.drawable.light_off);
		 } else {
			 nouvelle_Image= getResources().getDrawable(R.drawable.light_on);
		 }
		 
		 lampe.setImageDrawable(nouvelle_Image);
		 
	 }


}
