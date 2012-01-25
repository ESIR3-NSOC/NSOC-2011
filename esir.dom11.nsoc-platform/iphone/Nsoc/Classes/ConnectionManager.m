//
//  ConnectionManager.m
//  nsoc
//
//  Created by Pierre BARON on 25/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "ConnectionManager.h"
#import "ASIHTTPRequest.h"

@implementation ConnectionManager

@synthesize savedIp;
@synthesize savedPort;
@synthesize stateConnection;


- (NSString *) savedIp {
	return self.savedIp;
}

- (NSString *) savedPort {
	return self.savedPort;
}

- (void) setSavedIp:(NSString *) ip{
	savedIp = ip;
}
- (void) setSavedPort:(NSString *) port{
	savedPort = port;
}

- (BOOL) connectionToServer:(NSString *)ip portServer:(NSString *)port {
	//we save the ip and port in the Dictionnary
	savedIp = ip;
	savedPort = port;

	//we create the http string
	NSString *http = [NSString stringWithFormat:@"http://%1$@:%2$@", ip, port];
	NSLog(@"url = %@", http);
	NSURL *url = [NSURL URLWithString:http];
	ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:url];
	[request setDelegate:self];
	//[request startAsynchronous];
	//NSLog(@"stateconnection connectionToServer : %@", stateConnection);
	//return stateConnection;
	[request startSynchronous];
	
	NSError *error = [request error];
	if (!error) {
		return YES;
	} else {
		return NO;
	}

}

/**
 *	REST Requests
 */
/*
- (void) requestFinished:(ASIHTTPRequest *)request {
	// Use when fetching text data
	NSString *responseString = [request responseString];
	NSLog(@"%@", responseString);
	stateConnection = YES;
	NSLog(@"stateConnection : %@", stateConnection);
}

- (void) requestFailed:(ASIHTTPRequest *)request {

	NSError *error = [request error];
	NSLog(@"%@", error);
	stateConnection = NO;
	NSLog(@"stateConnection : %@", stateConnection);

}
*/

@end
