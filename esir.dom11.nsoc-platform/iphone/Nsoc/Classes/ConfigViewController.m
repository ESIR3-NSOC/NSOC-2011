//
//  ConfigViewController.m
//  Nsoc
//
//  Created by Pierre BARON on 14/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "NsocAppDelegate.h"
#import "ConfigViewController.h"
#import "ConnectionManager.h"

@implementation ConfigViewController

@synthesize ServerTableView;
@synthesize serverLabels;
@synthesize serverPlaceholders;
@synthesize cm;


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
	cm = [[ConnectionManager alloc] init];
	//create the info button on the navigation bar
	UIButton *infoButton = [UIButton buttonWithType:UIButtonTypeInfoLight];
	[infoButton addTarget:self 
				   action:@selector(showCredits:) 
		forControlEvents:UIControlEventTouchUpInside];
	self.navigationItem.rightBarButtonItem = [[[UIBarButtonItem alloc] initWithCustomView:infoButton] autorelease];
	
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
	connectionToServerBtn.frame = CGRectMake(10, [ServerTableView contentSize].height, 300, 40);
	[self.view addSubview:connectionToServerBtn];
	
	//Create the status label text
	UILabel *statusLabelText = [[UILabel alloc] init];
	statusLabelText.text = @"Connection status:";
	statusLabelText.font = [UIFont fontWithName:@"Helvetica" size:14];
	//the color is #323232 in hex.
	statusLabelText.textColor = [UIColor colorWithRed:(50.0/255.0) green:(50.0/255.0) blue:(50.0/255.0) alpha:1.0];
	statusLabelText.backgroundColor = [UIColor clearColor];
	statusLabelText.frame = CGRectMake(10, 
									   connectionToServerBtn.frame.origin.y + connectionToServerBtn.frame.size.height + 20, 
									   [statusLabelText.text sizeWithFont:statusLabelText.font].width, 
									   [statusLabelText.text sizeWithFont:statusLabelText.font].height);
	[self.view addSubview:statusLabelText];

	// Declaration of status label properties
	statusLabel = [[UILabel alloc] init];
	statusLabel.font = [UIFont fontWithName:@"Helvetica-Bold" size:14];
	statusLabel.backgroundColor = [UIColor clearColor];
	[self displayConnectionStatus:FALSE];

	//we block the scroll for the UITableView
	ServerTableView.scrollEnabled = NO;
    [super viewDidLoad];
}

// Create the UILabel of the connection status
-(void) displayConnectionStatus:(BOOL)state{
	if(state){
		statusLabel.text = @"Connection established";
		statusLabel.textColor = [UIColor colorWithRed:(48.0/255.0) green:(94/255.0) blue:(2.0/255.0) alpha:1.0];
		statusLabel.frame = CGRectMake(310 - [statusLabel.text sizeWithFont:statusLabel.font].width, 
									   connectionToServerBtn.frame.origin.y + connectionToServerBtn.frame.size.height + 20, 
									   [statusLabel.text sizeWithFont:statusLabel.font].width, 
									   [statusLabel.text sizeWithFont:statusLabel.font].height);		
	} else {
		statusLabel.text = @"Not connected";
		statusLabel.textColor = [UIColor colorWithRed:(205.0/255.0) green:(0.0/255.0) blue:(0.0/255.0) alpha:1.0];
		statusLabel.frame = CGRectMake(310 - [statusLabel.text sizeWithFont:statusLabel.font].width, 
									   connectionToServerBtn.frame.origin.y + connectionToServerBtn.frame.size.height + 20, 
									   [statusLabel.text sizeWithFont:statusLabel.font].width, 
									   [statusLabel.text sizeWithFont:statusLabel.font].height);
	}
	[self.view addSubview:statusLabel];

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
		[self displayConnectionStatus:returnStateConnection];
		
		NSDictionary *savedIpDict = [NSDictionary dictionaryWithObject:labelIpServer.text forKey:@"getSavedIp"];
		   [[NSUserDefaults standardUserDefaults] registerDefaults:savedIpDict];
				
		//save the data in the iPhone   
		//NsocAppDelegate *appDel = [[UIApplication sharedApplication] delegate];
		//appDel.savedIp = labelIpServer.text;
		//appDel.savedPort = labelPortServer.text;
		   
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
	
	//simulate a click on the return button to hide the keyboard
	[[(ELCTextfieldCell*)[self.ServerTableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:1 inSection:0]] rightTextField] resignFirstResponder];

}

// test if the entry matches the wanted format
-(BOOL) testEntryWithRegex:(NSString *)entry regex:(NSString *) regex{
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
- (void)configureCell:(ELCTextfieldCell *)cell atIndexPath:(NSIndexPath *)indexPath {
	
	//we configure the left and right label
	cell.leftLabel.text = [self.serverLabels objectAtIndex:indexPath.row];
	cell.rightTextField.placeholder = [self.serverPlaceholders objectAtIndex:indexPath.row];
	
	//we retrive the value saved in the iPhone
	NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
	
	if([indexPath row] == 0){
		cell.rightTextField.text = [prefs stringForKey:@"getSavedIp"];
		
	} else if([indexPath row] == 1){
		cell.rightTextField.text = [prefs stringForKey:@"getSavedPort"];
	} 

	cell.rightTextField.tag = (10 + [indexPath row]); 
	cell.indexPath = indexPath;
	cell.delegate = self;
	
	//Disables UITableViewCell from accidentally becoming selected.
	cell.selectionStyle = UITableViewCellEditingStyleNone;
	
	[cell.rightTextField setKeyboardType:UIKeyboardTypeNumbersAndPunctuation];

}

// Customize the number of rows in a UITableView
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger) section{
	if (section == 0) {
		return [serverLabels count];
	}else {
		return 0;
	}

}

// Display the title of a section in a UITableView
- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger) section{
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

// store the fields in an array on each update of the TextFields
- (void)updateTextLabelAtIndexPath:(NSIndexPath*)indexPath string:(NSString*)string {
	
	//NSLog(@"See input: %@ from section: %d row: %d, should update models appropriately", string, indexPath.section, indexPath.row);
}


// Action fired on info button click
- (IBAction) showCredits: (id)sender {
	CreditsViewController *creditsController = [[CreditsViewController alloc] initWithNibName:@"CreditsViewController" bundle:nil];
	creditsController.delegate = self;
	
	creditsController.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
	[self presentModalViewController:creditsController animated:YES];
	[creditsController release];
}

- (void) creditsViewControllerDidFinish:(CreditsViewController *)controller{
	[self dismissModalViewControllerAnimated:YES];
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
	[statusLabel dealloc];
    [super dealloc];
}


@end
