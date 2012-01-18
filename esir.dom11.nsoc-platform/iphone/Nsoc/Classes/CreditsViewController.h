//
//  CreditsViewController.h
//  Nsoc
//
//  Created by Pierre BARON on 14/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@protocol CreditsViewControllerDelegate;

@interface CreditsViewController : UIViewController {
	id <CreditsViewControllerDelegate> delegate;
}
@property (nonatomic, assign) id <CreditsViewControllerDelegate> delegate;

- (IBAction) done:(id) sender;
- (void)goToMail: (id)sender;
- (void) goToWebsite: (id)sender;
@end


@protocol CreditsViewControllerDelegate
-(void) creditsViewControllerDidFinish:(CreditsViewController *) controller;
@end
