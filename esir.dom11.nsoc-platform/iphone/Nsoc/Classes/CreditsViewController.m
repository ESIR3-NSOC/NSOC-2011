//
//  CreditsViewController.m
//  Nsoc
//
//  Created by Pierre BARON on 14/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "CreditsViewController.h"


@implementation CreditsViewController

@synthesize delegate;
@synthesize scrollView, contentView;

// dismiss the credits view
-(IBAction) done:(id) sender{
	[self.delegate creditsViewControllerDidFinish:self];
}


// Implements viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {	
	[super viewDidLoad];
	
	[self.view addSubview:scrollView];
	[self.scrollView addSubview:self.contentView];
	self.scrollView.contentSize = self.contentView.bounds.size;
}

// quit the app and launch the mail app
- (void)goToMail: (id)sender {
	UIButton *button = (UIButton * ) sender;
	NSURL *urlMail = [[NSURL alloc] initWithString:[NSString stringWithFormat:@"mailto://%@",button.currentTitle]];
	[[UIApplication sharedApplication] openURL:urlMail];
	
	[button release];
}

// quit the app and launch Safari
- (void) goToWebsite: (id)sender{
	UIButton *button = (UIButton * ) sender;
	NSURL *urlWeb = [[NSURL alloc] StringWithFormat:@"http://%@",button.currentTitle];
	[[UIApplication sharedApplication] openURL:urlWeb];
	
	[button release];
}

// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
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
	self.scrollView = nil;
	self.contentView = nil;
}


- (void)dealloc {
	[scrollView release];
	[contentView release];
	
    [super dealloc];
}


@end
