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
@synthesize COMFORTTEMP, idActionArray, idActuatorArray;


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	//initialize the values
	COMFORTTEMP = [[NSNumber alloc] initWithInt:20];
	idActionArray = [[NSArray alloc] initWithObjects:@"8e207b0a-c052-4e55-8aef-840eb73fe3eda", nil];
	idActuatorArray = [[NSArray alloc] initWithObjects:@"da5ca0b3-3139-48d1-baed-128cb3869568", nil];

	
	UIBarButtonItem *temperatureButton = [[UIBarButtonItem alloc] initWithTitle:@"Send" 
																	  style:UIBarButtonItemStylePlain
																	 target:self 
																	 action:@selector(sendTemperature:)];      
	self.navigationItem.rightBarButtonItem = temperatureButton;	
}

- (void) sendTemperature:(id) sender {
	ConnectionManager *cm = [[ConnectionManager alloc] init];
	BOOL command = [cm sendPostrequest:[idActionArray objectAtIndex:0] 
							idActuator:[idActuatorArray objectAtIndex:0]
							   datatype:@"temperature"
							  building:@"b7"
								  room:@"s930"
								 value:tempSlider.value];
	
	/*if (command) {
		
	} else{
		
	}*/
	
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
		
	// We firstly test the value of comfortTemp
	if([COMFORTTEMP intValue] > [[NSNumber numberWithFloat:tempSlider.maximumValue] intValue]){
		COMFORTTEMP = [[NSNumber numberWithFloat:tempSlider.maximumValue] intValue];
	} else if([COMFORTTEMP intValue] < [[NSNumber numberWithFloat:tempSlider.minimumValue] intValue]) {
		COMFORTTEMP =[[NSNumber numberWithFloat:tempSlider.minimumValue] intValue];
	}
	
	int temp = [COMFORTTEMP intValue] - temperature;
	
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
	[COMFORTTEMP release];
	[idActionArray release];
	[idActuatorArray release];
	
	[super dealloc];
}


@end
