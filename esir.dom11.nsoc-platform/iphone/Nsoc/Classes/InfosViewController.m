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
@synthesize tempInLabel, tempOutLabel, brightnessInLabel, brightnessOutLabel, co2Label, presenceLabel;

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]
											  initWithBarButtonSystemItem:UIBarButtonSystemItemRefresh
											  target:self
											  action:@selector(refreshInfo:)];
	
	[self.view addSubview:scrollView];
	[self.scrollView addSubview:self.contentView];
	self.scrollView.contentSize = self.contentView.bounds.size;
	[UIApplication sharedApplication].networkActivityIndicatorVisible = YES;

	[self performSelector:@selector(getData) 
			   withObject:nil 
			   afterDelay:0.1];

}


// receive the array of current data and parse it
-(void) getData {
	cm = [[ConnectionManager alloc] init];
	
	NSArray *results = [cm allData:@"bat7" room:@"salle930"];	
		
	if(!results){
		ConnectionViewController *cvc = [[ConnectionViewController alloc] 
										 initWithNibName:@"ConnectionViewController" bundle:nil];
		cvc.delegate = self;
		
		cvc.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
		[self presentModalViewController:cvc animated:YES];
		[cvc release];
		
	} else {
		for(int i = 0; i < ([results count]-1); i++) {
			NSArray *items = [[results objectAtIndex:i] componentsSeparatedByString:@":"];
			
			NSArray *locations = [[items objectAtIndex:0] componentsSeparatedByString:@"/"];
			NSString *actuator = [locations objectAtIndex:([locations count] -2)];
			NSString *number = [locations objectAtIndex:([locations count]-1)];
			NSString *value = [items objectAtIndex:1];		
			
			if([actuator isEqualToString:@"temp"]) {
				if([number isEqualToString:@"0"]){
					tempInLabel.text = value;
				} else if([number isEqualToString:@"1"]) {
					tempOutLabel.text = value;
				}
			}
			else if([actuator isEqualToString:@"lum"]){
				if([number isEqualToString:@"0"]){
					brightnessInLabel.text = value;
				} else if([number isEqualToString:@"1"]) {
					brightnessOutLabel.text = value;
				}	
			}
			else if([actuator isEqualToString:@"co2"]) {
				if([number isEqualToString:@"0"]){
					co2Label.text = value;
				}
			}
			else if([actuator isEqualToString:@"presence"]) {
				if([number isEqualToString:@"0"]){
					presenceLabel.text = value;
				}		
			}
		}
	}
}

// ask for a refresh of the information
- (void) refreshInfo:(id)sender {
	[self getData];
}


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
	self.brightnessOutLabel = nil;
	self.presenceLabel = nil;
	self.co2Label = nil;
}


- (void)dealloc {
	[cm release];
	[scrollView release];
	[contentView release];
	[tempInLabel release];
	[tempOutLabel release];
	[brightnessInLabel release];
	[brightnessOutLabel release];
	[presenceLabel release];
	[co2Label release];
	
    [super dealloc];
}

- (void) connectionViewControllerDidFinish:(ConnectionViewController *)controller{
	[self dismissModalViewControllerAnimated:YES];
}

@end
