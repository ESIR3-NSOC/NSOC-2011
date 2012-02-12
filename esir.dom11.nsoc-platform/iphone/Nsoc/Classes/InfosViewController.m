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
@synthesize scrollView;

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	[self.view addSubview:scrollView];
	
	cm = [[ConnectionManager alloc] init];
	/*BOOL result = [cm allData:@"bat7" room:@"salle930"];	
	
	if(!result){
		ConnectionViewController *connectionViewController = [[ConnectionViewController alloc] initWithNibName:@"ConnectionViewController" bundle:nil];
		connectionViewController.delegate = self;
		
		connectionViewController.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
		[self presentModalViewController:connectionViewController animated:YES];
		[connectionViewController release];
		
	}
	*/
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
}


- (void)dealloc {
    [super dealloc];
}

- (void) connectionViewControllerDidFinish:(ConnectionViewController *)controller{
	[self dismissModalViewControllerAnimated:YES];
}

@end
