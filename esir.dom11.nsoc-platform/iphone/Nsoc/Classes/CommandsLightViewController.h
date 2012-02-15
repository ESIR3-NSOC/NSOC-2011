//
//  CommandsLightViewController.h
//  nsoc
//
//  Created by Pierre BARON on 30/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ConnectionManager.h"

@interface CommandsLightViewController : UIViewController {
	UISwitch *diningSwitch;
	UISwitch *kitchenSwitch;
	UISwitch *bedroomSwitch;
	UISwitch *bedroomDimingSwitch;
	
	UIScrollView *scrollView;
	UIView *contentView;
}

@property (nonatomic, retain) IBOutlet UISwitch *diningSwitch;
@property (nonatomic, retain) IBOutlet UISwitch *kitchenSwitch;
@property (nonatomic, retain) IBOutlet UISwitch *bedroomSwitch;
@property (nonatomic, retain) IBOutlet UISwitch *bedroomDimingSwitch;

@property (nonatomic, retain) IBOutlet UIScrollView *scrollView;
@property (nonatomic, retain) IBOutlet UIView *contentView;


-(IBAction) changeValue:(id)sender;


@end
