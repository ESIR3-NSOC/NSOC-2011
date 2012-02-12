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
	UIView *contentView;
	
	UILabel *tempInLabel;
	UILabel *tempOutLabel;
	UILabel *brightnessInLabel;
	UILabel *humidityInLabel;
	
}

@property (nonatomic, retain) ConnectionManager *cm;
@property (nonatomic, retain) IBOutlet UIScrollView *scrollView;
@property (nonatomic, retain) IBOutlet UIView *contentView;

@property (nonatomic, retain) IBOutlet UILabel *tempInLabel;
@property (nonatomic, retain) IBOutlet UILabel *tempOutLabel;
@property (nonatomic, retain) IBOutlet UILabel *brightnessInLabel;
@property (nonatomic, retain) IBOutlet UILabel *humidityInLabel;


- (void) refreshInfo:(id) sender;

@end
