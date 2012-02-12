//
//  CommandsLightViewController.m
//  nsoc
//
//  Created by Pierre BARON on 30/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "CommandsLightViewController.h"


@implementation CommandsLightViewController

@synthesize dinningSwitch, kitchenSwitch, bedroomSwitch;

- (void)viewDidLoad {
	[super viewDidLoad];
	
	dinningSwitch.tag = 0;
	kitchenSwitch.tag = 1;
	bedroomSwitch.tag = 2;

}

-(IBAction) changeValue:(id)sender {
	UISwitch *switchOutlet = (UISwitch *) sender;
	ConnectionManager *cm = [[ConnectionManager alloc] init];
	int result = 0;
	if(switchOutlet.on){
		result = 1;
	}

	[cm sendPostRequest:[NSString stringWithFormat:@"%d", result] 
			   datatype:@"light" 
			   building:@"bat7" 
				   room:@"salle930" 
			   actuator:[NSString stringWithFormat:@"light%d", switchOutlet.tag]];

}

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
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
	self.dinningSwitch = nil;
	self.kitchenSwitch = nil;
	self.bedroomSwitch = nil;
	
}


- (void)dealloc {
	[dinningSwitch release];
	[kitchenSwitch release];
	[bedroomSwitch release];
	
    [super dealloc];
}


@end
