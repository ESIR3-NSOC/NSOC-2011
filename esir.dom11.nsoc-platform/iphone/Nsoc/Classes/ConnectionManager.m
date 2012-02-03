//
//  ConnectionManager.m
//  nsoc
//
//  Created by Pierre BARON on 25/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "ConnectionManager.h"
#import "ASIHTTPRequest.h"
#import "ASIFormDataRequest.h"

@implementation ConnectionManager

@synthesize savedIp;
@synthesize savedPort;
@synthesize stateConnection;


/**
 *	Getters/Setters
 */

// get the saved IP address in the iPhone 
- (NSString *) savedIp {
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	savedIp = [defaults objectForKey:@"savedIp"];
	
	if(savedIp == nil){
		savedIp = @"192.168.1.1";
	}
	return savedIp;
}

// get the saved port in the iPhone
- (NSString *) savedPort {
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	savedPort = [defaults objectForKey:@"savedPort"];
	
	if(savedPort == nil){
		savedPort = @"8182";
	}
	return savedPort;
}

// save the new IP address in the iPhone
- (void) setSavedIp:(NSString *) ip{
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	[defaults setObject:ip forKey:@"savedIp"];
    [defaults synchronize];	
	[defaults release];
	savedIp = ip;
}

// save the new port in the iPhone
- (void) setSavedPort:(NSString *) port{
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	[defaults setObject:port forKey:@"savedIp"];
    [defaults synchronize];	
	[defaults release];
	savedPort = port;
}


/**
 *	GET requests
 */

// send the GET request to know if we are connected to the server
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

// send the GET request to fetch all data
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

// send the GET request to fetch details for a DataType
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
 *	POST Requests
 */

// send the POST request to update a value
- (BOOL) sendPostrequest:(NSString *)idAction
			  idActuator:(NSString *)idActuator 
				   value:(double)value{
	
	//if there is a connection between the server and the client 
	if (![self connectionToServer:[self savedIp] portServer:[self savedPort]]) {
		return NO;
	} else {

		ASIFormDataRequest *request = [ASIFormDataRequest requestWithURL:
										[NSURL URLWithString: 
											[NSString stringWithFormat:@"http://%1$@:%2$@", [self savedIp], [self savedPort]]]];
		
		[request addPostValue:idAction forKey:@"idAction"];
		[request addPostValue:idActuator forKey:@"idActuator"];
		[request addPostValue:[NSString stringWithFormat:@"%f", value] forKey:@"value"];
		[request setDelegate:self];
		[request startAsynchronous];
		NSLog(@"command sent!");
	}
	return YES;
}



/**
 *	REST Requests
 */
/*
- (void) requestFinished:(ASIHTTPRequest *)request {
	// Use when fetching text data
	NSString *responseString = [request responseString];
	NSLog(@"%@", responseString);
}

- (void) requestFailed:(ASIHTTPRequest *)request {
	NSError *error = [request error];
	NSLog(@"%@", error);
}
 */

@end
