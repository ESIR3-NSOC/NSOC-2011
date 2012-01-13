//
//  nsocViewController.m
//  nsoc
//
//  Created by Pierre BARON on 11/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "nsocAppDelegate.h"
#import "nsocViewController.h"
#import "RKRequeteClient.h"

@implementation nsocViewController

@synthesize adrIpServer;
@synthesize portServer;
@synthesize connectionBtn;
@synthesize scrollAmount;
@synthesize hvc;

//enleve le clavier lors de l'appui sur Terminer
-(IBAction) goAwayKeyboard: (id) sender{
	[sender resignFirstResponder];
}

//enleve le claver lors de l'appui sur le background 
-(IBAction) tapBackground: (id) sender{
	[super resignFirstResponder];
}

- (IBAction) clickConnectionButton: (id) sender{
	//Creation d'un delegate pour lancer la fonction
	NSLog(@"/*** Lancement de la connexion au server ***/");
	
	//nsocAppDelegate *appDel = [nsocAppDelegate alloc];
	//[appDel createServer: adrIpServer.text, portServer.text];
	/*
	if(self.hvc == nil){
		HomeViewController *homeVC = [[HomeViewController alloc] initWithNibName:@"HomeViewController" bundle:nil];
		self.hvc = homeVC;
		[homeVC release];
	}
	
	[self.navigationController pushViewController:self.hvc animated:YES];
	*/
	
	[self presentModalViewController:hvc animated:YES];
	
}

/*
// The designated initializer. Override to perform setup that is required before the view is loaded.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if ((self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil])) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/



// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
}

-(void) viewWillAppear:(BOOL) animated{
	[[NSNotificationCenter defaultCenter] addObserver:self
											 selector:@selector(keyboardWillShow:)
												 name:UIKeyboardWillShowNotification
											   object:self.view.window];
}


-(void) viewWillDisappear:(BOOL) animated{
	[[NSNotificationCenter defaultCenter] removeObserver:self 
											  forKeyPath:UIKeyboardWillShowNotification
												  object:nil];
	
	[super viewWillDisappear:animated];
}

-(void) keyboardWillShow:(NSNotification *) notif{
	NSDictionary* info = [notif userInfo];
	NSValue* aValue = [info objectForKey: UIKeyboardBoundsUserInfoKey];
	CGSize keyboardSize = [aValue CGRectValue].size;
	float bottomPoint = (portServer.frame.origin.y + portServer.frame.size.height+10);
	scrollAmount = keyboardSize.height - (self.view.frame.size.height - bottomPoint);
	
	if (scrollAmount > 0) {
		moveViewUp = YES;
		[self scrollTheView:YES];
	} else {
		moveViewUp = NO;
	}
}

-(void) scrollTheView:(BOOL) movedUp {
	[UIView beginAnimations:nil context:NULL];
	[UIView setAnimationDuration:0.3];
	CGRect rect = self.view.frame;
	
	if (movedUp) {
		rect.origin.y -= scrollAmount;
	} else {
		rect.origin.y += scrollAmount;
	}
	
	self.view.frame = rect;
	[UIView commitAnimations];

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

@end
