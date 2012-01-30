//
//  PBTableViewCell.m
//  nsoc
//
//  Created by Pierre BARON on 30/01/12.
//  Copyright 2012 ESIR. All rights reserved.
//

#import "PBTableViewCell.h"

@implementation PBTableViewCell

@synthesize leftTitle;
@synthesize leftSubtitle;
@synthesize switchOutlet;


- (id)initWithStyle:(UITableViewCellStyle)style 
	reuseIdentifier:(NSString *)reuseIdentifier {
    
	if ((self = [super initWithStyle:style reuseIdentifier:reuseIdentifier])) {
		
		leftTitle = [[UILabel alloc] initWithFrame:CGRectZero];
		[leftTitle setBackgroundColor:[UIColor clearColor]];
		[leftTitle setTextColor:[UIColor colorWithRed:.285 green:.376 blue:.541 alpha:1]];
		[leftTitle setFont:[UIFont fontWithName:@"Helvetica" size:12]];
		[leftTitle setTextAlignment:UITextAlignmentRight];
		[leftTitle setText:@"Left Field"];
		[self addSubview:leftTitle];
		
		leftSubtitle = [[UILabel alloc] initWithFrame:CGRectZero];
		[leftSubtitle setBackgroundColor:[UIColor clearColor]];
		[leftSubtitle setTextColor:[UIColor colorWithRed:.285 green:.376 blue:.541 alpha:1]];
		[leftSubtitle setFont:[UIFont fontWithName:@"Helvetica" size:12]];
		[leftSubtitle setTextAlignment:UITextAlignmentRight];
		[leftSubtitle setText:@"Left Field"];
		[self addSubview:leftSubtitle];
		
		
		switchOutlet = [[UISwitch alloc] initWithFrame:CGRectZero];
		switchOutlet.contentVerticalAlignment = UIControlContentVerticalAlignmentCenter;
		switchOutlet.tag = 1;
		[switchOutlet setDelegate:self];

		[self addSubview:switchOutlet];
    }
	
    return self;
}

@end
