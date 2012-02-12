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

- (void)goToMail: (id)sender{
	NSURL *urlMail = [[NSURL alloc] initWithString:@"mailto://prbaron22@gmail.com"];
	[[UIApplication sharedApplication] openURL:urlMail];
}

- (void) goToWebsite: (id)sender{
	//initialisation des url
	NSURL *urlWeb = [[NSURL alloc] initWithString: @"http://pierrebaron.fr"];
	
	//actions des url
	[[UIApplication sharedApplication] openURL:urlWeb];
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
