//
//  nsocAppDelegate.h
//  nsoc
//
//  Created by Pierre BARON on 11/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <RestKit/RestKit.h>

@class nsocViewController;

@interface nsocAppDelegate : NSObject <UIApplicationDelegate> {
	RKClient *client;
    UIWindow *window;
    nsocViewController *viewController;
}

-(void) sendPostRequest: (NSDictionary *) dictionary;
-(void) createServer: (NSString *) ipServer;

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet nsocViewController *viewController;
@property (nonatomic, retain) IBOutlet RKClient *client;

@end

