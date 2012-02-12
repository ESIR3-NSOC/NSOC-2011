package librarytests;

import java.awt.Rectangle;
import org.openkinect.processing.Kinect;
import processing.core.PApplet; import processing.core.PImage;
public class RemoveBackground extends PApplet {
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

	int frontThreshold = 500;

	int backThreshold = 800;

	int depthXPicOffset = 30;
	int depthYPicOffset = 30;

	public void setup() {

		size(w, h);
		kinect = new Kinect(this);
		kinect.start();
		kinect.enableDepth(true);
		kinect.enableRGB(true);

		// We don't need the grayscale image in this example
		// so this makes it more efficient
		kinect.processDepthImage(false);

	}

	public void draw() {
		background(255, 0, 0);
		loadPixels();

		fill(255);
		PImage myImage = kinect.getVideoImage();
		// Get the raw depth as array of integers
		int[] depth = kinect.getRawDepth();
		// We're just going to calculate and draw every 4th pixel (equivalent of 160x120)
		int skip = 1;

		for (int x = 0; x < w; x += skip) {
			for (int y = 0; y < h; y += skip) {
				int offset = x + y * w;
				int rawDepth = depth[offset];
				if (rawDepth > frontThreshold && rawDepth < backThreshold) {
					int imageOffset = x - depthXPicOffset + (y + depthYPicOffset) * w;
					if (imageOffset < myImage.pixels.length) (pixels[offset] )  = myImage.pixels[imageOffset] ;
				}
			}
		}
		updatePixels();

	}

	public void keyPressed() {
		if (key == '-') {
			frontThreshold--;
		} else if (key == '=') {
			frontThreshold++;
		}
		if (key == '_') {
			backThreshold--;
		} else if (key == '+') {
			backThreshold++;
		}
		if (key == 'x') {
			depthXPicOffset--;
		} else if (key == 'X') {
			depthXPicOffset++;
		}else if (key == 'y') {
			depthYPicOffset--;
		} else if (key == 'y') {
			depthYPicOffset++;
		}
		println("Threshold between:" + frontThreshold + " and " + backThreshold + " depthPicOffset" + depthXPicOffset + " " + depthXPicOffset);
	}

	public void stop() {
		kinect.quit();
		super.stop();
	}
}