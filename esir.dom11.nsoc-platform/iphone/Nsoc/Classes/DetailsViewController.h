//
//  DetailsViewController.h
//  Nsoc
//
//  Created by Pierre BARON on 14/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface DetailsViewController : UIViewController {
	UITableView *DetailsTableView;
	NSArray *detailsArray;
}
@property (nonatomic, retain) IBOutlet UITableView *DetailsTableView;
@property (nonatomic, retain) NSArray *detailsArray;

@end
