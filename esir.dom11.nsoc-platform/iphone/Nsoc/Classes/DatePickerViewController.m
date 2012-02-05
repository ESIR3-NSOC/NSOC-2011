//
//  DatePickerViewController.m
//  nsoc
//
//  Created by Pierre BARON on 23/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "DatePickerViewController.h"
#import "DataTypeDetailsViewController.h"

@implementation DatePickerViewController

@synthesize delegate;
@synthesize rangeTableView;
@synthesize rangeArray;
@synthesize dateArray;
@synthesize dateLabel;

@synthesize picker;

@synthesize data;

/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	self.rangeArray = [NSArray arrayWithObjects:@"Starts", @"Ends", nil];
	
	//get the current date
	NSDate *currentDate = [NSDate date];
	
	NSTimeInterval month = -31*24*60*60;
	NSDate *currentDateMinusMonth = [NSDate dateWithTimeIntervalSinceNow:month];
	
	self.dateArray = [[NSMutableArray alloc] initWithCapacity:2];
	[self.dateArray addObject:currentDateMinusMonth];
	[self.dateArray addObject:currentDate];
	
	rangeTableView.scrollEnabled = NO;
	[rangeTableView selectRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0] animated:NO scrollPosition:0];

	data = [[ModelData alloc] initWithDate:[self.dateArray objectAtIndex:0] 
								   endDate:[self.dateArray objectAtIndex:1]];
}

-(IBAction) clickCancelButton {
	[self.delegate DatePickerViewControllerDidFinish:self];
}

-(IBAction) clickDoneButton {
	DataTypeDetailsViewController *dtdController = [[DataTypeDetailsViewController alloc] initWithDataFromDatePicker:data];
	
	[self.navigationController pushViewController:dtdController animated:YES];
    [dtdController release];

	[self.delegate DatePickerViewControllerDidFinish:self];
}

/*
 *	DatePicker methods
 */

-(IBAction) datePickerValueChanged {
	
	//save the new Date in the array
	[self.dateArray replaceObjectAtIndex:[[rangeTableView indexPathForSelectedRow] row]
						 withObject:picker.date];
	
	//create the new date format with the new date
	NSDateFormatter *dateFormat = [[[NSDateFormatter alloc] init] autorelease];
	[dateFormat setDateFormat:@"MMM. d, YYYY"];
	
	//refresh the new date
	UITableViewCell *cell = (UITableViewCell *)[rangeTableView cellForRowAtIndexPath:[rangeTableView indexPathForSelectedRow]];            
	dateLabel = (UILabel *) [cell viewWithTag:(10+[[rangeTableView indexPathForSelectedRow] row])]; 
	dateLabel.text = [dateFormat stringFromDate:[self.dateArray objectAtIndex:[[rangeTableView indexPathForSelectedRow] row]]];
	
	//prepare the data to send to the view
	data = [[ModelData alloc] initWithDate:[self.dateArray objectAtIndex:0] 
								   endDate:[self.dateArray objectAtIndex:1]];
}

/**
 *	UITableView methods
 */

-(NSInteger) numberOfSectionsInTableView:(UITableView *) tableView{
	return 1;
}

// Customize the number of rows in a UITableView
-(NSInteger)tableView:(UITableView *)tableView 
numberOfRowsInSection:(NSInteger) section{
	return [rangeArray count];	
}


// Return a cell containing the text to display at the provided row index
-(UITableViewCell *)tableView:(UITableView *)tableView 
		cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	
	//We initialize a NSString
	static NSString *cellIdentifier = @"DetailsCell";
	
	//We create the cell
	UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
	
	if(cell == nil){
		cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault
									   reuseIdentifier:cellIdentifier] autorelease];
		
	}
	
	cell.textLabel.text = [self.rangeArray objectAtIndex:[indexPath row]];
	
	//We create the TextField
	dateLabel = [[UILabel alloc] initWithFrame:CGRectMake(160, 10, 140, 30)];
	dateLabel.textAlignment = UITextAlignmentRight;
	
	dateLabel.textColor = [UIColor colorWithRed:(69.0/255.0) green:(92.0/255.0) blue:(124.0/255.0) alpha:1.0];
	dateLabel.highlightedTextColor = [UIColor colorWithRed:(255.0/255.0) green:(255.0/255.0) blue:(255.0/255.0) alpha:1.0];
	dateLabel.tag = (10 + [indexPath row]);
	
	NSDateFormatter *dateFormat = [[[NSDateFormatter alloc] init] autorelease];
	[dateFormat setDateFormat:@"MMM. d, YYYY"];
	
	dateLabel.text = [dateFormat stringFromDate:[self.dateArray objectAtIndex:[indexPath row]]];

	[cell addSubview:dateLabel];
	[dateLabel release];
	return cell;
}

// method launched on the click of one of the cell
//
// will launch the DatePicker
-(void)tableView:(UITableView *)tableView 
didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	[picker setDate:[self.dateArray objectAtIndex:[indexPath row]] animated:YES];
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
