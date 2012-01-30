//
//  CommandsTemperatureViewController.m
//  nsoc
//
//  Created by Pierre BARON on 30/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "CommandsTemperatureViewController.h"


@implementation CommandsTemperatureViewController

@synthesize comfortBtn;
@synthesize ecoBtn;
@synthesize lessonBtn;
@synthesize tempSlider;
@synthesize tempLabel;
@synthesize COMFORTTEMP;

/*
 // The designated initializer.  
 Override if you create the controller programmatically and 
 want to perform customization that is not appropriate for viewDidLoad.

 - (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	//initialize the values
	COMFORTTEMP = [[NSNumber alloc] initWithInt:20];
}


/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
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


- (void)dealloc {
	[comfortBtn release];
	[ecoBtn release];
	[lessonBtn release];
    [tempSlider release];
	[tempLabel release];
	[COMFORTTEMP release];
	
	[super dealloc];
}


@end
