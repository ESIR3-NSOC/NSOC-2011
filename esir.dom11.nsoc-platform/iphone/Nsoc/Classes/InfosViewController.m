//
//  InfosViewController.m
//  Nsoc
//
//  Created by Pierre BARON on 14/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "ConnectionManager.h"
#import "InfosViewController.h"

@implementation InfosViewController

@synthesize cm;
@synthesize scrollView, contentView;
@synthesize tempInLabel, tempOutLabel, brightnessInLabel, humidityInLabel;

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	[self.view addSubview:scrollView];
	[self.scrollView addSubview:self.contentView];
	self.scrollView.contentSize = self.contentView.bounds.size;
	
	cm = [[ConnectionManager alloc] init];
	
	NSArray *results = [cm allData:@"bat7" room:@"salle930"];	
	
	if(!results){
		ConnectionViewController *connectionViewController = [[ConnectionViewController alloc] initWithNibName:@"ConnectionViewController" bundle:nil];
		connectionViewController.delegate = self;
		
		connectionViewController.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
		[self presentModalViewController:connectionViewController animated:YES];
		[connectionViewController release];
		
	} else {
		for(int i = 0; i < ([results count]-1); i++) {
			NSArray *item = [[results objectAtIndex:i] componentsSeparatedByString:@":"];
			
			NSArray *location = [[item objectAtIndex:0] componentsSeparatedByString:@"/"];
			NSString *sensor = [location objectAtIndex:([location count]-1)];
			NSString *value = [item objectAtIndex:1];			
			
			if([sensor isEqualToString:@"tempInt"]){
				tempInLabel.text = value;

			} else if([sensor isEqualToString:@"tempOut"]) {
				tempOutLabel.text = value;
			} else if ([sensor isEqualToString:@"brightness"]) {
				brightnessInLabel.text = value;
			} else if([sensor isEqualToString:@"humidity"]) {
				humidityInLabel.text = value;
			}
		}
	}
		
	self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]
											  initWithBarButtonSystemItem:UIBarButtonSystemItemRefresh
											  target:self
											  action:@selector(refreshInfo:)];	
}


- (void) refreshInfo:(id)sender {
	[cm allData:@"bat7" room:@"salle930"];	
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
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
	self.cm = nil;
	self.scrollView = nil;
	self.contentView = nil;
	self.tempInLabel = nil;
	self.tempOutLabel = nil;
	self.brightnessInLabel = nil;
	self.humidityInLabel = nil;
	
}


- (void)dealloc {
	[cm release];
	[scrollView release];
	[contentView release];
	[tempInLabel release];
	[tempOutLabel release];
	[brightnessInLabel release];
	[humidityInLabel release];
	
    [super dealloc];
}

- (void) connectionViewControllerDidFinish:(ConnectionViewController *)controller{
	[self dismissModalViewControllerAnimated:YES];
}

@end
