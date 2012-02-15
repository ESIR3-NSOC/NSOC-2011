//
//  CommandsViewController.m
//  Nsoc
//
//  Created by Pierre BARON on 14/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "CommandsViewController.h"
#import "CommandsLightViewController.h"
#import "CommandsTemperatureViewController.h"
#import "CommandsBlindViewController.h"

@implementation CommandsViewController

@synthesize CommandsTableView;
@synthesize commandsArray;


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	self.commandsArray = [NSArray arrayWithObjects:@"Lights", @"Temperature", @"Blinds", @"Scenarii", nil];
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
	return [commandsArray count];	
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
	
	cell.textLabel.text = [self.commandsArray objectAtIndex:indexPath.row];
	return cell;
}

/*
 *	Method fired on the click of one of the cell
 */
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
	//click on Lightings
	if([indexPath row] == 0){
		CommandsLightViewController *controller = [[CommandsLightViewController alloc] init];
		[controller setTitle:[self.commandsArray objectAtIndex:[indexPath row]]];
		[[self navigationController] pushViewController:controller animated:YES];
		[controller release];
	}	
	
	//click on Temperature
	else if([indexPath row] == 1){
		CommandsTemperatureViewController *controller = [[CommandsTemperatureViewController alloc] init];
		[controller setTitle:[self.commandsArray objectAtIndex:indexPath.row]];
		[[self navigationController] pushViewController:controller animated:YES];
		[controller release];
	}
	
	//click on Blinds
	else if([indexPath row] == 2){
		CommandsBlindViewController *controller = [[CommandsBlindViewController alloc] init];
		[controller setTitle:[self.commandsArray objectAtIndex:indexPath.row]];
		[[self navigationController] pushViewController:controller animated:YES];
		[controller release];
	}
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
	self.CommandsTableView = nil;
	self.commandsArray = nil;
	
}


- (void)dealloc {
	[CommandsTableView release];
	[commandsArray release];
    [super dealloc];
}


@end
