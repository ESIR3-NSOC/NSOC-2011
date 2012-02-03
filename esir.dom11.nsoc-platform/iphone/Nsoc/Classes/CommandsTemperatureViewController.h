//
//  CommandsTemperatureViewController.h
//  nsoc
//
//  Created by Pierre BARON on 30/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface CommandsTemperatureViewController : UIViewController {
	UIButton *comfortBtn;
	UIButton *ecoBtn;
	UIButton *lessonBtn;
	UISlider *tempSlider;
	
	UILabel *tempLabel;
	
	NSNumber *COMFORTTEMP;
	NSArray *idActionArray;
	NSArray *idActuatorArray;
}

@property (nonatomic, retain) IBOutlet UIButton *comfortBtn;
@property (nonatomic, retain) IBOutlet UIButton *ecoBtn;
@property (nonatomic, retain) IBOutlet UIButton *lessonBtn;
@property (nonatomic, retain) IBOutlet UISlider *tempSlider;
@property (nonatomic, retain) IBOutlet UILabel *tempLabel;
@property (nonatomic, retain) NSNumber *COMFORTTEMP;

@property (nonatomic, retain) NSArray *idActionArray;
@property (nonatomic, retain) NSArray *idActuatorArray;

- (void) sendTemperature:(id) sender;
- (IBAction)sliderValueChanged:(id)sender;
- (IBAction)setComfortTemperature:(id)sender;
- (IBAction)setEcoTemperature:(id)sender;
- (IBAction)setLessonTemperature:(id)sender;
- (void)setTemperature:(int)temperature;
		   
@end
