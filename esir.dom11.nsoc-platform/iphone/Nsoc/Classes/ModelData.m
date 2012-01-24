//
//  ModelData.m
//  nsoc
//
//  Created by Pierre BARON on 24/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "ModelData.h"


@implementation ModelData

@synthesize beginDate;
@synthesize endDate;


- (id)initWithDate:(NSDate *)aBeginDate endDate:(NSDate *)aEndDate{
	self.beginDate = aBeginDate;
	self.endDate = aEndDate;
	
	return self;
}

@end
