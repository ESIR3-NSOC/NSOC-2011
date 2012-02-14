//
//  CommandsLightViewController.m
//  nsoc
//
//  Created by Pierre BARON on 30/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "CommandsLightViewController.h"


@implementation CommandsLightViewController

@synthesize diningSwitch, kitchenSwitch, bedroomSwitch, bedroomDimingSwitch;

- (void)viewDidLoad {
	[super viewDidLoad];
	
	diningSwitch.tag = 0;
	kitchenSwitch.tag = 1;
	bedroomSwitch.tag = 2;
	bedroomDimingSwitch.tag = 100;
	
	
	// display the current data of the switches
	ConnectionManager *cm = [[ConnectionManager alloc] init];
	NSArray *results = [cm allData:@"bat7" room:@"salle930"];	
	if (results) {
		for(int i = 0; i < ([results count]-1); i++) {
			NSArray *items = [[results objectAtIndex:i] componentsSeparatedByString:@":"];
			
			NSArray *locations = [[items objectAtIndex:0] componentsSeparatedByString:@"/"];
			NSString *actuator = [locations objectAtIndex:([locations count] -2)];
			NSString *number = [locations objectAtIndex:([locations count]-1)];
			NSString *value = [items objectAtIndex:1];			
			
			if([actuator isEqualToString:@"switch"]){
				if([number isEqualToString:@"0"]){
					if([value isEqualToString:@"ON"])
						[diningSwitch setOn:YES animated:YES];
					else [diningSwitch setOn:NO animated:YES];
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
	
}

//on ne peut pas envoyer 2 commandes à la fois
-(IBAction) changeValue:(id)sender {
	UISwitch *switchOutlet = (UISwitch *) sender;
	ConnectionManager *cm = [[ConnectionManager alloc] init];
	NSMutableString *result = [[NSMutableString alloc] initWithString:@"OFF"];
	NSMutableString *actuator = [[NSMutableString alloc] initWithString:@"lamp/0"];
	
	// for scenario switch
	if (switchOutlet.tag >= 100) {
		[actuator setString:@"switch/0"];
		
		// we have to set the value
		if(switchOutlet.on){
			[result setString:@"UP"];
		} else{
			[result setString:@"DOWN"];
		}
	} 
	// for normal switches
	else {
		[actuator setString:[NSString stringWithFormat:@"lamp/%d", switchOutlet.tag]];
		
		// we have to set the value
		if(switchOutlet.on){
			[result setString:@"ON"];
		} else if(switchOutlet.on){
			[result setString:@"OFF"];
		}
	}
	
	[cm sendPostRequest:result 
			   datatype:@"switch" 
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
	self.diningSwitch = nil;
	self.kitchenSwitch = nil;
	self.bedroomSwitch = nil;
	
}


- (void)dealloc {
	[diningSwitch release];
	[kitchenSwitch release];
	[bedroomSwitch release];
	
    [super dealloc];
}


@end
