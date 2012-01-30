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
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	savedIp = [defaults objectForKey:@"savedIp"];
	
	if(savedIp == nil){
		savedIp = @"192.168.1.1";
	}
	return savedIp;
}

- (NSString *) savedPort {
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	savedPort = [defaults objectForKey:@"savedPort"];
	
	if(savedPort == nil){
		savedPort = @"8182";
	}
	return savedPort;
}

- (void) setSavedIp:(NSString *) ip{
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	[defaults setObject:ip forKey:@"savedIp"];
    [defaults synchronize];	
	[defaults release];
	savedIp = ip;
}

- (void) setSavedPort:(NSString *) port{
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	[defaults setObject:port forKey:@"savedIp"];
    [defaults synchronize];	
	[defaults release];
	savedPort = port;
}

- (BOOL) connectionToServer:(NSString *)ip portServer:(NSString *)port {	
    // Store the data in the phone
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	[defaults setObject:ip forKey:@"savedIp"];
    [defaults setObject:port forKey:@"savedPort"];
    [defaults synchronize];	
	[defaults release];
		
	//we create the http string
	NSString *http = [NSString stringWithFormat:@"http://%1$@:%2$@", ip, port];
	NSLog(@"url = %@", http);
	NSURL *url = [NSURL URLWithString:http];
	ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:url];
	[request setDelegate:self];
	[request startSynchronous];
		
	NSError *error = [request error];
	if (!error) {
		return YES;
	} else {
		return NO;
	}

}

- (void) allData{
	// client ip : http://@IP:port/all/building/room/
	NSString *http = [NSString stringWithFormat:@"http://%1$@:%2$@/all/b7/s930", 
												[self savedIp], 
												[self savedPort]];
	NSLog(@"url = %@", http);
	NSURL *url = [NSURL URLWithString:http];
	ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:url];
	[request setDelegate:self];
	[request startAsynchronous];
	
	
}

- (void) allDataFromDatatype:(NSString *)datatypeForRequest 
				   beginDate:(NSDate *)beginDateForRequest 
					 endDate:(NSDate *)endDateForRequest {
	
	// client ip : http://@IP:port/detail/building/room/dataType/beginDate/endDate/
	NSString *http = [NSString stringWithFormat:@"http://%1$@:%2$@/detail/b7/s930/%3$@/%$4@/%$5@", 
												[self savedIp], 
												[self savedPort],
												datatypeForRequest,
												beginDateForRequest,
												endDateForRequest];
	NSLog(@"url = %@", http);
	NSURL *url = [NSURL URLWithString:http];
	ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:url];
	[request setDelegate:self];
	[request startSynchronous];
}

/**
 *	REST Requests
 */

- (void) requestFinished:(ASIHTTPRequest *)request {
	// Use when fetching text data
	NSString *responseString = [request responseString];
	NSLog(@"%@", responseString);
}

- (void) requestFailed:(ASIHTTPRequest *)request {
	NSError *error = [request error];
	NSLog(@"%@", error);
}

@end
