//
//  CommandsLightViewController.h
//  nsoc
//
//  Created by Pierre BARON on 30/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface CommandsLightViewController : UIViewController {
	UISwitch *dinningSwitch;
	UISwitch *kitchenSwitch;
	UISwitch *bedroomSwitch;
}

@property (nonatomic, retain) IBOutlet UISwitch *dinningSwitch;
@property (nonatomic, retain) IBOutlet UISwitch *kitchenSwitch;
@property (nonatomic, retain) IBOutlet UISwitch *bedroomSwitch;

-(IBAction) changeValue:(id) sender;

@end
