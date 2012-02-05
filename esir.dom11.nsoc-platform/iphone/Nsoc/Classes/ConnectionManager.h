//
//  ConnectionManager.h
//  nsoc
//
//  Created by Pierre BARON on 25/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ConnectionManager : NSObject {
	NSString *savedIp;
	NSString *savedPort;
	BOOL stateConnection;
}

@property (nonatomic, retain) NSString *savedIp;
@property (nonatomic, retain) NSString *savedPort;
@property (nonatomic) BOOL stateConnection;

/**
 *	Getters/Setters
 */
- (NSString *) savedIp;
- (NSString *) savedPort;
- (void) setSavedIp:(NSString *) ip;
- (void) setSavedPort:(NSString *) port;

/**
 *	GET requests
 */
- (BOOL) connectionToServer:(NSString *)ip portServer:(NSString *)port;
- (void) allData:(NSString *)building 
			room:(NSString *)room;

- (void) allDataFromDatatype:(NSString *)datatype 
					building:(NSString *)building
						room:(NSString *)room
				   beginDate:(NSDate *)beginDate 
					 endDate:(NSDate *)endDate;

/**
 *	POST Requests
 */
- (BOOL) sendPostrequest:(NSString *)idAction 
			  idActuator:(NSString *)idActuator
				datatype:(NSString *)datatype
				building:(NSString *)building
					room:(NSString *)room
				   value:(double)value;


@end
