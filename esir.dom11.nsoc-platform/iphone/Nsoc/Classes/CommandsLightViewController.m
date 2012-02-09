//
//  CommandsLightViewController.m
//  nsoc
//
//  Created by Pierre BARON on 30/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "CommandsLightViewController.h"
#import "ConnectionManager.h"

@implementation CommandsLightViewController

@synthesize dinningSwitch, kitchenSwitch, bedroomSwitch;

-(IBAction) changeValue:(id)sender {
	UISwitch *switchOutlet = (UISwitch *) sender;
	ConnectionManager *cm = [[ConnectionManager alloc] init];
	int result = 0;
	if(switchOutlet.on){
		result = 1;
	}
	
	NSLog(@"result :%d", result);
	[cm sendPostrequest:result 
			   datatype:@"light" 
			   building:@"bat7" 
				   room:@"salle930" 
			   actuator:@"light"];
}

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


- (void)dealloc {
	[dinningSwitch release];
	[kitchenSwitch release];
	[bedroomSwitch release];
	
    [super dealloc];
}


@end
