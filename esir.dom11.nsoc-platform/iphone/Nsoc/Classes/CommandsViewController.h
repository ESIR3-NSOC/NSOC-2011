//
//  CommandsViewController.h
//  Nsoc
//
//  Created by Pierre BARON on 14/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface CommandsViewController : UIViewController {
	UITableView *CommandsTableView;
	NSArray *commandsArray;
}

@property (nonatomic, retain) IBOutlet UITableView *CommandsTableView;
@property (nonatomic, retain) NSArray *commandsArray;

@end
