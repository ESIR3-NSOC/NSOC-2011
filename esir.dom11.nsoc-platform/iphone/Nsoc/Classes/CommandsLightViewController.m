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

	
	// display the current data of the switches
	ConnectionManager *cm = [[ConnectionManager alloc] init];
	NSArray *results = [cm allData:@"bat7" room:@"salle930"];	
	
	for(int i = 0; i < ([results count]-1); i++) {
		NSArray *items = [[results objectAtIndex:i] componentsSeparatedByString:@":"];
		
		NSArray *locations = [[items objectAtIndex:0] componentsSeparatedByString:@"/"];
		NSString *actuator = [locations objectAtIndex:([locations count] -2)];
		NSString *number = [locations objectAtIndex:([locations count]-1)];
		NSString *value = [items objectAtIndex:1];			
		
		if([actuator isEqualToString:@"switch"]){
			if([number isEqualToString:@"0"]){
				if([value isEqualToString:@"ON"])
					[dinningSwitch setOn:YES animated:YES];
				else [dinningSwitch setOn:NO animated:YES];
			} 
			else if([number isEqualToString:@"1"]) {
				if([value isEqualToString:@"ON"])
					[kitchenSwitch setOn:YES animated:YES];
				else [kitchenSwitch setOn:NO animated:YES];
			}
			else if([number isEqualToString:@"2"]) {
				if([value isEqualToString:@"ON"])
					[bedroomSwitch setOn:YES animated:YES];
				else [bedroomSwitch setOn:NO animated:YES];
			}
		}
	}
}

//on ne peut pas envoyer 2 commandes à la fois
-(IBAction) changeValue:(id)sender {
	UISwitch *switchOutlet = (UISwitch *) sender;
	ConnectionManager *cm = [[ConnectionManager alloc] init];
	NSMutableString *result = [[NSMutableString alloc] initWithString:@"OFF"];

	if(switchOutlet.on){
		[result setString:@"ON"];
	}
	
	NSString *actuator = [NSString stringWithFormat:@"lamp/%d", switchOutlet.tag];

	[cm sendPostRequest:result 
			   datatype:@"light" 
			   building:@"bat7" 
				   room:@"salle930" 
			   actuator:actuator];
	
	[cm release];
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
