//
//  nsocAppDelegate.h
//  nsoc
//
//  Created by Pierre BARON on 11/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class nsocViewController;

@interface nsocAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    nsocViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet nsocViewController *viewController;

@end

