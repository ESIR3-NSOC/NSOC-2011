package librarytests;

import org.openkinect.*;
import org.openkinect.processing.*;

import processing.core.PApplet;
import processing.core.PVector;

public class AveragePointTracking extends PApplet{


	// Showing how we can farm all the kinect stuff out to a separate class
	KinectTracker tracker;
	// Kinect Library object
	Kinect kinect;

	public void setup() {
	  size(640,520);
	  kinect = new Kinect(this);
	  tracker = new KinectTracker();
	}

	public void draw() {
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

	  // Display some info
	  int t = tracker.getThreshold();
	  fill(0);
	  text("threshold: " + t + "    " +  "framerate: " + (int)frameRate + "    " + "UP increase threshold, DOWN decrease threshold",10,500);
	}

	public void keyPressed() {
	  int t = tracker.getThreshold();
	  if (key == CODED) {
	    if (keyCode == UP) {
	      t+=5;
	      tracker.setThreshold(t);
	    } 
	    else if (keyCode == DOWN) {
	      t-=5;
	      tracker.setThreshold(t);
	    }
	  }
	}

	public void stop() {
	  tracker.quit();
	  super.stop();
	}

}
