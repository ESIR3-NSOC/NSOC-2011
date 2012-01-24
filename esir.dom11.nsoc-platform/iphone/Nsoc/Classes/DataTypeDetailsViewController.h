//
//  DataTypeDetailsViewController.h
//  nsoc
//
//  Created by Pierre BARON on 18/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DatePickerViewController.h"
#import "ModelData.h"

@interface DataTypeDetailsViewController : UIViewController <DatePickerViewControllerDelegate> {
	ModelData *dataFromDate;
}

@property (nonatomic, retain) ModelData *dataFromDate;

- (IBAction) showDate;
- (id)initWithDataFromDatePicker:(ModelData *)passedData;


@end
