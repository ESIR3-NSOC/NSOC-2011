package applicationKinect;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JFrame;

import processing.core.PApplet;

/**
 * The application which can control the kinect
 * @author Laurent Cabon
 *
 */
public class KinectApp extends Frame{

	/**
	 * Constructor
	 */
	public KinectApp() {
		super("Application Kinect");
		setLayout(new BorderLayout());
		PApplet embed = new KinectGui();
		add(embed,BorderLayout.CENTER);
		
		// Initialize the embedded PApplet
		embed.init();
	}
}
