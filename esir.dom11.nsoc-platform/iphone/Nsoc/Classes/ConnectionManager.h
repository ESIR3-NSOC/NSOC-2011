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

- (NSString *) savedIp;
- (NSString *) savedPort;
- (void) setSavedIp:(NSString *) ip;
- (void) setSavedPort:(NSString *) port;
- (BOOL) connectionToServer:(NSString *)ip portServer:(NSString *)port;

@end
