//
//  InfosViewController.h
//  Nsoc
//
//  Created by Pierre BARON on 14/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//
#import <UIKit/UIKit.h>
#import "ConnectionManager.h"


@interface InfosViewController : UIViewController {
	ConnectionManager *cm;
}

@property (nonatomic, retain) ConnectionManager *cm;

- (void) refreshInfo:(id) sender;

@end
