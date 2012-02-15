//
//  CommandsBlindViewController.m
//  nsoc
//
//  Created by Pierre BARON on 15/02/12.
//  Copyright 2012 Pierre Baron. All rights reserved.
//

#import "CommandsBlindViewController.h"


@implementation CommandsBlindViewController

@synthesize diningLeftUpButton, diningLeftDownButton;


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	// init the tags
	// the hundred determines the actuator value to send
	// If you want to send to the address /shutter/1 -> tag = 100, 101, ...
	diningLeftUpButton.tag = 000;
	diningLeftDownButton.tag = 001;

}

// fired on a up click
-(IBAction) OpenBlind:(id) sender {
	UIButton *button = (UIButton *)sender;
	int buttonTag  = button.tag /= 100;
	NSLog(@"buttonTag: %d", buttonTag);
	
	[self sendRequest:@"OPEN" actuator:buttonTag];
	[button release];
}

// fired on a down click
-(IBAction) closeBlind:(id) sender {
	UIButton *button = (UIButton *)sender;
	int buttonTag  = floor(button.tag /= 100);
	NSLog(@"buttonTag: ", buttonTag);
	
	[self sendRequest:@"CLOSE" actuator:buttonTag];
	[button release];
	
}

// send the POST request
-(void) sendRequest:(NSString *)value 
		   actuator:(int)actuator
{
	ConnectionManager *cm = [[ConnectionManager alloc] init];
	NSString *actu = [NSString stringWithFormat:@"shutter/%d", actuator];
	
	[cm sendPostRequest:value 
			   datatype:@"shutter" 
			   building:@"bat7" 
				   room:@"salle930" 
			   actuator:actu];
	
	[cm release];
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
	
	self.diningLeftUpButton = nil;
	self.diningLeftDownButton = nil;
}


- (void)dealloc {
	[diningLeftUpButton release];
	[diningLeftDownButton release];
	
    [super dealloc];
}


@end
