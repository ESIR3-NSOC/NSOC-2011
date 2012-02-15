//
//  ConnectionViewController.h
//  nsoc
//
//  Created by Pierre BARON on 10/02/12.
//  Copyright 2012 ESIR. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ConnectionManager.h"
#import "ELCTextfieldCell.h"

@protocol ConnectionViewControllerDelegate;

@interface ConnectionViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, ELCTextFieldDelegate> {
	id <ConnectionViewControllerDelegate> delegate;
	
	UITableView *ServerTableView;
	
	NSArray *serverLabels;
	NSArray *serverPlaceholders;
	
	UIButton *connectionToServerBtn;
	
	ConnectionManager *cm;
	
}
@property (nonatomic, assign) id <ConnectionViewControllerDelegate> delegate;

@property (nonatomic, retain) IBOutlet UITableView *ServerTableView;
@property (nonatomic, retain) NSArray *serverLabels;
@property (nonatomic, retain) NSArray *serverPlaceholders;
@property (nonatomic, retain) UIButton *connectionToServerBtn;
@property (nonatomic, retain) ConnectionManager *cm;

- (void) configureCell:(UITableViewCell *)cell atIndexPath:(NSIndexPath *)indexPath;
- (BOOL) testEntryWithRegex:(NSString *)entry regex:(NSString *) regex;
- (IBAction) connectionToServer:(id) sender;
- (IBAction) dismissView:(id)sender;

@end

@protocol ConnectionViewControllerDelegate
-(void) connectionViewControllerDidFinish:(ConnectionViewController *) controller;
@end