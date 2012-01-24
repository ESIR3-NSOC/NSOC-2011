//
//  DataTypeDetailsViewController.m
//  nsoc
//
//  Created by Pierre BARON on 18/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "NsocAppDelegate.h"
#import "DataTypeDetailsViewController.h"


@implementation DataTypeDetailsViewController

@synthesize dataFromDate;

/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/

- (id)initWithDataFromDatePicker:(ModelData *) passedData{
	
	if(self = [self initWithNibName:@"DataTypeDetailsViewController" bundle:nil]){
		self.dataFromDate = passedData;
		NSLog(@"Begin Date : %@", self.dataFromDate.beginDate);
		NSLog(@"End Date : %@", self.dataFromDate.endDate);

	}
	
	UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(10, 70, 200, 30)];
	NSDateFormatter *dateFormatter = [[[NSDateFormatter alloc] init] autorelease]; 
	[dateFormatter setDateFormat:@"dd-MM-YYYY"];
	//label.text = [dateFormatter stringFromDate:self.dataFromDate.beginDate];
	label.text = @"coucou";
	
	return self;
}


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];

	//create the calendar button on the navigation bar
	UIButton *calendarButton = [UIButton buttonWithType:UIButtonTypeCustom];
	[calendarButton setBackgroundImage:[UIImage imageNamed:@"calendar.png"] forState:UIControlStateNormal];
	[calendarButton addTarget:self
					   action:@selector(showDate) 
			 forControlEvents:UIControlEventTouchUpInside];
	[calendarButton setFrame:CGRectMake(0, 0, 20, 20)];
	
	self.navigationItem.rightBarButtonItem = [[[UIBarButtonItem alloc] initWithCustomView:calendarButton] autorelease];
	
}


// Action fired on date button click
- (IBAction) showDate{
	DatePickerViewController *dateController = [[DatePickerViewController alloc] initWithNibName:@"DatePickerViewController" bundle:nil];
	dateController.delegate = self;
	
	dateController.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
	[self presentModalViewController:dateController animated:YES];
	[dateController release];
}


- (void) DatePickerViewControllerDidFinish:(DatePickerViewController *)controller{
	[self dismissModalViewControllerAnimated:YES];
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
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (void)dealloc {
    [super dealloc];
}


@end
