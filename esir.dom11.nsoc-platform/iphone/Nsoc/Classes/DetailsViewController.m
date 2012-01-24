//
//  DetailsViewController.m
//  Nsoc
//
//  Created by Pierre BARON on 14/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "DetailsViewController.h"
#import "DataTypeDetailsViewController.h"


@implementation DetailsViewController

@synthesize DetailsTableView;
@synthesize detailsArray;

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
	
	self.detailsArray = [NSArray arrayWithObjects:@"Temperature", @"Humidity", @"Luminosity", @"Carbone Dioxyde", nil];
	
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
	return [detailsArray count];	
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
		
		[cell setAccessoryType:UITableViewCellAccessoryDisclosureIndicator];
	}
	
	cell.textLabel.text = [self.detailsArray objectAtIndex:indexPath.row];
	return cell;
}

// method launched on the click of one of the cell
//
// will create the DataTypeDetailsViewController
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	DataTypeDetailsViewController *controller = [[DataTypeDetailsViewController alloc] init];
	
	[controller setTitle:[self.detailsArray objectAtIndex:indexPath.row]];
	
	[[self navigationController] pushViewController:controller animated:YES];
	[controller release];
}


/*
 We don't need to uncomment this method due to the tabBarController, others orientations than portrait don't work
 
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
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
