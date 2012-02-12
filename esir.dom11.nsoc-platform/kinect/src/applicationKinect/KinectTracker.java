package applicationKinect;

import org.openkinect.LEDStatus;
import org.openkinect.processing.Kinect;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class KinectTracker {

	static PApplet p;

	Kinect kinect;

	// Taille de l'image
	int kw = 640;
	int kh = 480;
	
	int threshold = 600;

	// Raw location
	PVector loc;

	// Interpolated location
	PVector lerpedLoc;

	// Depth data
	int[] depth;

	// Degre
	int deg = 0;

	PImage display;

	public KinectTracker() {
		kinect = new Kinect(p);
		kinect.start();
		kinect.enableDepth(true);
		
		// Calibration du degré de la caméra
		kinect.tilt(deg);
		
		// Mise en place de la kinect en mode détecteur de mouvement (rouge)
		kinect.led(LEDStatus.LED_RED);
	
		// We could skip processing the grayscale image for efficiency
		// but this example is just demonstrating everything
		kinect.processDepthImage(true);

		display = p.createImage(kw,kh,PConstants.RGB);

		loc = new PVector(0,0);
		lerpedLoc = new PVector(0,0);
	}

	public void track() {

		// Get the raw depth as array of integers
		depth = kinect.getRawDepth();

		// Being overly cautious here
		if (depth == null) return;

		float sumX = 0;
		float sumY = 0;
		float count = 0;

		for(int x = 0; x < kw; x++) {
			for(int y = 0; y < kh; y++) {
				// Mirroring the image
				int offset = kw-x-1+y*kw;
				// Grabbing the raw depth
				int rawDepth = depth[offset];

				// Testing against threshold
				if (rawDepth < threshold){
					sumX += x;
					sumY += y;
					count++;
				} 
			}
		}
		// As long as we found something
		if (count != 0) {
			loc = new PVector(sumX/count,sumY/count);
		}

		// Interpolating the location, doing it arbitrarily for now
		lerpedLoc.x = PApplet.lerp(lerpedLoc.x, loc.x, 0.3f);
		lerpedLoc.y = PApplet.lerp(lerpedLoc.y, loc.y, 0.3f);

	}

	public PVector getLerpedPos() {
		return lerpedLoc;
	}

	public PVector getPos() {
		return loc;
	}
	
	public void setLed(LEDStatus led){
		kinect.led(led);
	}
	
	public void setTilt(int deg){
		kinect.tilt(deg);
	}

	public void display() {
		PImage img = kinect.getDepthImage();

		//PImage img = kinect.getVideoImage();
		// Being overly cautious here
		if (depth == null || img == null) return;

		// Going to rewrite the depth image to show which pixels are in threshold
		// A lot of this is redundant, but this is just for demonstration purposes
		display.loadPixels();
		for(int x = 0; x < kw; x++) {
			for(int y = 0; y < kh; y++) {
				// mirroring image
				int offset = kw-x-1+y*kw;
				// Raw depth
				int rawDepth = depth[offset];

				int pix = x+y*display.width;
				if (rawDepth < threshold){
					// A red color instead
					display.pixels[pix] = p.color(150,50,50);
				} else {
					display.pixels[pix] = img.pixels[offset];
				}
			}

		}
		display.updatePixels();

		// Draw the image
		p.image(display,0,0);
	}

	public void quit() {
		kinect.quit();
		kinect.led(LEDStatus.LED_BLINK_GREEN);
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int t) {
		threshold =  t;
	}


}
