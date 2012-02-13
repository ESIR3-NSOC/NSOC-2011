package applicationKinect;

import org.openkinect.LEDStatus;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import rest.*;

public class KinectGui extends PApplet {

	KinectTracker tracker;
		
	PVector coordo;
	
	// Position initiale du tracker
	//PVector posInit = tracker.getPos();

	public void setup() {
		size(640,520);
		KinectTracker.p = this;
		tracker = new KinectTracker();
	}

	public void draw() {
		
		coordo = tracker.getPos();
		
		background(255);
		
		// Run the tracking analysis
		tracker.track();
		// Show the image
		tracker.display();
		
		// Let's draw the raw location
		PVector v1 = tracker.getPos();
		fill(50,100,250,200);
		noStroke();
		ellipse(v1.x,v1.y,20,20);
		
		// Let's draw the "lerped" location
		PVector v2 = tracker.getLerpedPos();
		fill(100,250,50,200);
		noStroke();
		ellipse(v2.x,v2.y,20,20);
		
		// Enregistre la position initiale du vecteur position
		// Rappel sur les coordonées de base 
		// [ 319.50183, 239.11406, 0.0 ]
				
		// Display some info
		int t = tracker.getThreshold();
		//PVector coordonnees = tracker.getPos();
		
		fill(0);
		//text("Seuil : " + t + "    " +  "    /  " + "Coordonnées :" + coordo,10,500);
		
		isSomeone();
	}
	
	// Send Request to the server
	public void LightOn(){
		// Connect to the Rest server
		ConnexionManager connexion = new ConnexionManager();
		String datatype = "light";
		String building = "bat7";
		String room = "salle930";
		String idActuator = "da5ca0b3-3139-48d1-baed-128cb3869568";
		String value = "1";
		String idAction = "8e207b0a-c052-4e55-8aef-840eb73fe3eda";
		boolean command = connexion.sendRequest(idAction, idActuator, datatype, building, room, value);
		if(command == true){
			System.out.println("Command sent");
		}
		else {
			System.out.println("Error when sending the command");
		}
	}
	
	// Send Request to the server
	public void LightOff(){
		// Connect to the Rest server
		ConnexionManager connexion = new ConnexionManager();
		String datatype = "light";
		String building = "bat7";
		String room = "salle930";
		String idActuator = "da5ca0b3-3139-48d1-baed-128cb3869568";
		String value = "0";
		String idAction = "8e207b0a-c052-4e55-8aef-840eb73fe3eda";
		boolean command = connexion.sendRequest(idAction, idActuator, datatype, building, room, value);
		if(command == true){
			System.out.println("Command sent");
		}
		else {
			System.out.println("Error when sending the command");
		}	
	}
	
	// Event callback (detect someone)
	public void isSomeone(){
		
		// Coordonnées initiales du vecteur
		PVector coord = coordo; 
		
		// 
		if(!(coord.equals(tracker.getPos()))){
			
			//System.out.println("Anciennes coordonnées"+coord);
			//System.out.println("Nouvelles coordonnées"+tracker.getPos());
			
			// Led Kinect + motor
			tracker.setLed(LEDStatus.LED_YELLOW);
			//tracker.setTilt(30);
			
			// Send a request to the rest server
			//this.LightOn();
			
			//System.out.println("Détection d'un individu");
			
			// 
		}
		else if(coord.equals(tracker.getPos())){
			
			//System.out.println("Anciennes coordonnées"+coord);
			//System.out.println("Nouvelles coordonnées"+tracker.getPos());
			
			tracker.setLed(LEDStatus.LED_GREEN);
			//tracker.setTilt(20);
			
			// Send a request to the rest server
			//this.LightOff();
			
			//System.out.println("Personne n'est présent");
		}
	}
	
	
	public void keyPressed() {
		PVector coord = tracker.getPos();

		int t = tracker.getThreshold();
		if (key == CODED) {
			if (keyCode == UP) {
				t+=5;
				tracker.setThreshold(t);
			} else if (keyCode == DOWN) {
				t-=5;
				tracker.setThreshold(t);
			}
		}
	}
	
	public void stop() {
		tracker.quit();
		super.stop();
	}
	
	/**** Standard Run ****/
	static public void main(String _args[]) {
		//PApplet.main(new String[] { "--present", myClassName });
		PApplet.main(new String[] { myClassName });
	}

	protected static String myClassName = getQualifiedClassName();

	public static String getQualifiedClassName() {
		return new Exception().getStackTrace()[1].getClassName();
	}

}