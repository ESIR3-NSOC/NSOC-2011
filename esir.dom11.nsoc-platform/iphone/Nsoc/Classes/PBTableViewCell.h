//
//  PBTableViewCell.h
//  nsoc
//
//  Created by Pierre BARON on 30/01/12.
//  Copyright 2012 ESIR. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface PBTableViewCell : UITableViewCell {
	UILabel *leftTitle;
	UILabel *leftSubtitle;

	UISwitch *switchOutlet;
}

@property (nonatomic, retain) UILabel *leftTitle;
@property (nonatomic, retain) UILabel *leftSubtitle;
@property (nonatomic, retain) UISwitch *switchOutlet;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier;

@end
