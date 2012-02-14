//
//  CommandsBlindViewController.h
//  nsoc
//
//  Created by Pierre BARON on 15/02/12.
//  Copyright 2012 Pierre Baron. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ConnectionManager.h"

@interface CommandsBlindViewController : UIViewController {
	UIButton *diningLeftUpButton;
	UIButton *diningLeftDownButton;
}

@property (nonatomic, retain) IBOutlet UIButton *diningLeftUpButton;
@property (nonatomic, retain) IBOutlet UIButton *diningLeftDownButton;

-(IBAction) OpenBlind:(id) sender;
-(IBAction) closeBlind:(id) sender;
-(void) sendRequest:(NSString *)value 
		   actuator:(int)actuator;

@end
