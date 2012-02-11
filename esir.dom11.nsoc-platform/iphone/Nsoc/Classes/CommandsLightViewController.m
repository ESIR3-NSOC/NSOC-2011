//
//  CommandsLightViewController.m
//  nsoc
//
//  Created by Pierre BARON on 30/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "CommandsLightViewController.h"


@implementation CommandsLightViewController

<<<<<<< HEAD
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

	[cm sendPostrequest:result 
			   datatype:@"light" 
			   building:@"bat7" 
				   room:@"salle930" 
			   actuator:[NSString stringWithFormat:@"light%d", switchOutlet.tag]];
=======
/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}
*/

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
>>>>>>> 9bfec18a965cf347409b50613ad36ac5fc0b9f3b
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


- (void)dealloc {
    [super dealloc];
}


@end
