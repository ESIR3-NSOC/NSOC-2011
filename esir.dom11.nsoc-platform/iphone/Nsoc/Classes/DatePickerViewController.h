//
//  DatePickerViewController.h
//  nsoc
//
//  Created by Pierre BARON on 23/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ModelData.h"

@protocol DatePickerViewControllerDelegate;

@interface DatePickerViewController : UIViewController {
	id <DatePickerViewControllerDelegate> delegate;
	
	UITableView *rangeTableView;
	NSArray *rangeArray;
	NSMutableArray *dateArray;
	UILabel *dateLabel;
	
	UIDatePicker *picker;
	
	ModelData *data;
}

@property (nonatomic, assign) id <DatePickerViewControllerDelegate> delegate;
@property (nonatomic, retain) IBOutlet UITableView *rangeTableView;
@property (nonatomic, retain) NSArray *rangeArray;
@property (nonatomic, retain) NSMutableArray *dateArray;
@property (nonatomic, retain) UILabel *dateLabel;

@property (nonatomic, retain) IBOutlet UIDatePicker *picker;

@property (nonatomic, retain) ModelData *data;

-(IBAction) clickCancelButton;
-(IBAction) clickDoneButton;
-(IBAction) datePickerValueChanged;

@end

@protocol DatePickerViewControllerDelegate
-(void) DatePickerViewControllerDidFinish:(DatePickerViewController *) controller;
@end