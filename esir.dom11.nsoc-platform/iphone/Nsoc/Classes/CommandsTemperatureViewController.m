//
//  CommandsTemperatureViewController.m
//  nsoc
//
//  Created by Pierre BARON on 30/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "CommandsTemperatureViewController.h"
#import "ConnectionManager.h"

@implementation CommandsTemperatureViewController

@synthesize comfortBtn, ecoBtn, lessonBtn, tempSlider, tempLabel;

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	//initialize the values
	COMFORTTEMP = 20;
	
	UIBarButtonItem *temperatureButton = [[UIBarButtonItem alloc] initWithTitle:@"Send" 
																	  style:UIBarButtonItemStylePlain
																	 target:self 
																	 action:@selector(sendTemperature:)];      
	self.navigationItem.rightBarButtonItem = temperatureButton;	
}

- (void) sendTemperature:(id) sender {
	ConnectionManager *cm = [[ConnectionManager alloc] init];	
	[cm sendPostRequest:tempLabel.text 
			   datatype:@"temperature" 
			   building:@"bat7" 
				   room:@"salle930" 
			   actuator:@"temperature"];
	
	[cm release];
}



/*
 * Method fired on value change on the slider
 */
- (IBAction)sliderValueChanged:(id)sender{
	tempLabel.text = [NSString stringWithFormat:@"%d", [[NSNumber numberWithFloat:tempSlider.value] intValue]];
}

/*
 * Method fired on comfort button click
 */
- (IBAction)setComfortTemperature:(id)sender{
	[self setTemperature:0];
}

/*
 * Method fired on eco button click
 */
- (IBAction)setEcoTemperature:(id)sender{
	[self setTemperature:3];
}

/*
 * Method fired on lesson button click
 */
- (IBAction)setLessonTemperature:(id)sender{
	[self setTemperature:2];
}

/*
 * this method will change the command of temperature
 */
- (void)setTemperature:(int)temperature{
	
	int temp = COMFORTTEMP - temperature;
	
	//we edit the values of the outlets
	tempSlider.value = temp;
	tempLabel.text = [NSString stringWithFormat:@"%d", temp];
}

-
(void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)dealloc {
	[comfortBtn release];
	[ecoBtn release];
	[lessonBtn release];
    [tempSlider release];
	[tempLabel release];
	
	[super dealloc];
}


@end
