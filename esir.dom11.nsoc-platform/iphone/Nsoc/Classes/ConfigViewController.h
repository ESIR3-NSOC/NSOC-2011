//
//  ConfigViewController.h
//  Nsoc
//
//  Created by Pierre BARON on 14/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CreditsViewController.h"
#import "ELCTextfieldCell.h"
#import "ConnectionManager.h"

@interface ConfigViewController : UIViewController <CreditsViewControllerDelegate, UITableViewDelegate, UITableViewDataSource, ELCTextFieldDelegate> {
	UITableView *ServerTableView;
	
	NSArray *serverLabels;
	NSArray *serverPlaceholders;
	
	NSInteger *TableViewHeight;
	UILabel *statusLabel;
	UIButton *connectionToServerBtn;
														
	ConnectionManager *cm;
}

@property (nonatomic, retain) IBOutlet UITableView *ServerTableView;
@property (nonatomic, retain) NSArray *serverLabels;
@property (nonatomic, retain) NSArray *serverPlaceholders;
@property (nonatomic, retain) ConnectionManager *cm;

- (void) configureCell:(UITableViewCell *)cell atIndexPath:(NSIndexPath *)indexPath;
- (BOOL) testEntryWithRegex:(NSString *)entry regex:(NSString *) regex;
- (void) displayConnectionStatus:(BOOL)state;
- (IBAction) connectionToServer:(id) sender;
- (IBAction) showCredits:(id) sender;

@end
