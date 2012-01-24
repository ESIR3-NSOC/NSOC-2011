//
//  ModelData.h
//  nsoc
//
//  Created by Pierre BARON on 24/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface ModelData : NSObject {
	NSDate *beginDate;
	NSDate *endDate;
}

@property (nonatomic, retain) NSDate *beginDate;
@property (nonatomic, retain) NSDate *endDate;

- (id)initWithDate:(NSDate *)aBeginDate endDate:(NSDate *)aEndDate;

@end
