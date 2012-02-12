//
//  InfosViewController.h
//  Nsoc
//
//  Created by Pierre BARON on 14/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ConnectionManager.h"
#import "ConnectionViewController.h"

@interface InfosViewController : UIViewController <ConnectionViewControllerDelegate>{
	ConnectionManager *cm;
	UIScrollView *scrollView;
}

@property (nonatomic, retain) ConnectionManager *cm;
@property (nonatomic, retain) IBOutlet UIScrollView *scrollView;

- (void) refreshInfo:(id) sender;

@end
