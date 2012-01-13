//
//  nsocViewController.h
//  nsoc
//
//  Created by Pierre BARON on 11/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HomeViewController.h"

@interface nsocViewController : UIViewController {
	UITextField *adrIpServer;
	UITextField *portServer;
	UIButton *connectionBtn;
	HomeViewController *hvc;
	
	BOOL moveViewUp;
	CGFloat scrollAmount;
}

@property(nonatomic, retain) IBOutlet UITextField *adrIpServer;
@property(nonatomic, retain) IBOutlet UITextField *portServer;
@property(nonatomic, retain) IBOutlet UIButton *connectionBtn;
@property(nonatomic, retain) IBOutlet HomeViewController *hvc;
@property(nonatomic, retain) CGFloat scrollAmount;

-(IBAction) goAwayKeyboard: (id) sender;
-(IBAction) tapBackground: (id) sender;
-(IBAction) clickConnectionButton: (id) sender;
-(void) scrollTheView:(BOOL)movedUp;

@end

