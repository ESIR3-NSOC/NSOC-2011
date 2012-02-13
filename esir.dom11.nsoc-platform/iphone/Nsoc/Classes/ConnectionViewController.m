//
//  ConnectionViewController.m
//  nsoc
//
//  Created by Pierre BARON on 10/02/12.
//  Copyright 2012 ESIR. All rights reserved.
//

#import "ConnectionViewController.h"

@implementation ConnectionViewController

@synthesize delegate;
@synthesize ServerTableView;
@synthesize serverLabels, serverPlaceholders;
@synthesize connectionToServerBtn;
@synthesize cm;

// Implements viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];

	//instanciate a ConnectionManager
	cm = [[ConnectionManager alloc] init];
	
	//create the form in the UITableView
	self.serverLabels = [NSArray arrayWithObjects:@"IP Server",
						 @"Port Server",
						 nil];
	
	self.serverPlaceholders = [NSArray arrayWithObjects:@"192.168.1.1",
							   @"8182",
							   nil];
	
	//create the connection Button
	connectionToServerBtn = [UIButton buttonWithType:UIButtonTypeRoundedRect];
	[connectionToServerBtn addTarget:self 
							  action:@selector(connectionToServer:)
					forControlEvents:UIControlEventTouchUpInside];
	[connectionToServerBtn setTitle:@"Connection" forState:UIControlStateNormal];
	[ServerTableView layoutIfNeeded];
	connectionToServerBtn.frame = CGRectMake(10, 200, 300, 40);
	[self.view addSubview:connectionToServerBtn];
	
	//we block the scroll for the UITableView
	ServerTableView.scrollEnabled = NO;
	   
	[super viewDidLoad];
	
}

// action on the connectionToServer button click.
- (IBAction)connectionToServer:(id) sender{	
	
	//store the ip and port from the TextField
	UITableViewCell *cellIp = (UITableViewCell *)[ServerTableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]];            
	UITableViewCell *cellPort = (UITableViewCell *)[ServerTableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:1 inSection:0]];            
	UILabel *labelIpServer = (UILabel *) [cellIp viewWithTag:10];
	UILabel *labelPortServer =  (UILabel *) [cellPort viewWithTag:11]; 	
	
	//test if the ip and port are in the good format
	if([self testEntryWithRegex:labelIpServer.text
						  regex: @"^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"]
	   
	   && [self testEntryWithRegex:labelPortServer.text
							 regex:@"^[0-9]{4,5}$"]){
		   
		   BOOL returnStateConnection = [cm connectionToServer:labelIpServer.text portServer:labelPortServer.text];
		   if(!returnStateConnection){
			   //display an error
			   UIAlertView *message = [[UIAlertView alloc] initWithTitle: @"Error"  
																 message: @"The iPhone cannot connect the server, please verify the IP address and port"  
																delegate: nil  
													   cancelButtonTitle: @"Retry"  
													   otherButtonTitles: nil];  
			   
			   [message show];
			   [message release];
			   
		   }else {
			   NSLog(@"ok");
			   [self.delegate connectionViewControllerDidFinish:self];
		   }
	   } else{
		   //display an error
		   UIAlertView *message = [[UIAlertView alloc] initWithTitle: @"Error"  
															 message: @"Please, enter the right IP and port to connect to the server"  
															delegate: nil  
												   cancelButtonTitle: @"OK"  
												   otherButtonTitles: nil];  
		   
		   [message show];
		   [message release];
		   
	   }
	
	[cellIp release];
	[cellPort release];
	[labelIpServer release];
	[labelPortServer release];	
}

// test if the entry matches the wanted format
-(BOOL) testEntryWithRegex:(NSString *)entry 
					 regex:(NSString *) regex {
	NSError *error = NULL;
	
	NSRegularExpression *reg = [NSRegularExpression regularExpressionWithPattern: regex
																		 options:NSRegularExpressionCaseInsensitive 
																		   error:&error];	
	NSRange range = [reg rangeOfFirstMatchInString:entry
										   options:0
											 range:NSMakeRange(0, [entry length])];
	
	// if the entry matches the regex														 
	if(!NSEqualRanges(range, NSMakeRange(NSNotFound, 0))){
		return TRUE;
	} else {
		return FALSE;
	}
	
	[error release];
	[reg release];
}

/**
 *	UITableView methods
 */

// Create the design of the cells
- (void)configureCell:(ELCTextfieldCell *)cell 
		  atIndexPath:(NSIndexPath *)indexPath {
	
	//we configure the left and right label
	cell.leftLabel.text = [self.serverLabels objectAtIndex:indexPath.row];
	cell.rightTextField.placeholder = [self.serverPlaceholders objectAtIndex:indexPath.row];

	
	if([indexPath row] == 0){
		cell.rightTextField.text = [cm savedIp];
	} else if([indexPath row] == 1){
		cell.rightTextField.text = [cm savedPort];
	} 
	
	cell.rightTextField.tag = (10 + [indexPath row]); 
	cell.indexPath = indexPath;
	cell.delegate = self;
	
	//Disables UITableViewCell from accidentally becoming selected.
	cell.selectionStyle = UITableViewCellEditingStyleNone;
	
	[cell.rightTextField setKeyboardType:UIKeyboardTypeNumbersAndPunctuation];
	
}

// Customize the number of rows in a UITableView
-(NSInteger)tableView:(UITableView *)tableView 
numberOfRowsInSection:(NSInteger) section{
	if (section == 0) {
		return [serverLabels count];
	}else {
		return 0;
	}
	
}

// Display the title of a section in a UITableView
- (NSString *)tableView:(UITableView *)tableView 
titleForHeaderInSection:(NSInteger) section{
	if(section == 0){
		return @"Server settings";
	} else {
		return @"false";
	}
} 

// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView 
		 cellForRowAtIndexPath:(NSIndexPath *)indexPath {
	
	static NSString *CellIdentifier = @"Cell";
    
    ELCTextfieldCell *cell = (ELCTextfieldCell*)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[ELCTextfieldCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
	
	[self configureCell:cell atIndexPath:indexPath];

    return cell;
}

// Allow the return button to go in the next field
-(void)textFieldDidReturnWithIndexPath:(NSIndexPath*)indexPath {
	
	if(indexPath.row < [serverLabels count]-1) {
		NSIndexPath *path = [NSIndexPath indexPathForRow:indexPath.row+1 inSection:indexPath.section];
		[[(ELCTextfieldCell*)[self.ServerTableView cellForRowAtIndexPath:path] rightTextField] becomeFirstResponder];
		[self.ServerTableView scrollToRowAtIndexPath:path atScrollPosition:UITableViewScrollPositionTop animated:YES];
	}
	
	else {
		
		[[(ELCTextfieldCell*)[self.ServerTableView cellForRowAtIndexPath:indexPath] rightTextField] resignFirstResponder];
	}
}


- (void)updateTextLabelAtIndexPath:(NSIndexPath*)indexPath string:(NSString*)string {}


- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
	
	self.ServerTableView = nil;
	self.serverLabels = nil;
	self.serverPlaceholders = nil;
	self.cm = nil;
}


- (void)dealloc {
	[ServerTableView release];
	[serverLabels release];
	[serverPlaceholders release];
	[connectionToServerBtn release];
	[cm release];
	
    [super dealloc];
}

@end
