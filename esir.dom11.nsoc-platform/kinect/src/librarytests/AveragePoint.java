package librarytests;

import org.openkinect.processing.Kinect;
import processing.core.PApplet;
public class AveragePoint extends PApplet {
    // Dan O'Sullivan based on 
	// Daniel Shiffman
	// Kinect Point Cloud example
	// http://www.shiffman.net
	// https://github.com/shiffman/libfreenect/tree/master/wrappers/java/processing

	// Kinect Library object
	Kinect kinect;

	// Size of kinect image
	int w = 640;

	int h = 480;

	int threshold = 550;

	public void setup() {
		size(w, h);
		kinect = new Kinect(this);
		kinect.start();
		kinect.enableDepth(true);

		// We don't need the grayscale image in this example
		// so this makes it more efficient
		kinect.processDepthImage(false);
		ellipseMode(CENTER);

	}

	public void draw() {

		background(0);
		fill(255);
		textMode(SCREEN);
		text("Kinect FR: " + (int) kinect.getDepthFPS() + "\nProcessing FR: " + (int) frameRate, 10, 16);

		// Get the raw depth as array of integers
		int[] depth = kinect.getRawDepth();

		int allX = 0;
		int allY = 0;
		int all = 0;

		for (int x = 0; x < w; x ++) {
			for (int y = 0; y < h; y ++) {
				int offset = x + y * w;
				int rawDepth = depth[offset];
				if (depth == null) return;
				if (rawDepth < threshold) {
					allX += x;
					allY += y;
					all++;
				}
			}

		}
		if (all != 0) ellipse(width - allX / all, allY / all, 20, 20);

	}

	public void keyPressed() {
		if (key == '-') {
			threshold--;
		} else if (key == '=') {
			threshold++;
		}
		println("Threshold:" + threshold);
	}

	public void stop() {
		kinect.quit();
		super.stop();
	}
}