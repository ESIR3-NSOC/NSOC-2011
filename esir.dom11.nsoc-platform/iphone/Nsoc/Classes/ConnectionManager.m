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
	
	savedIp = ip;
}

// save the new port in the iPhone
- (void) setSavedPort:(NSString *) port{
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	[defaults setObject:port forKey:@"savedIp"];
    [defaults synchronize];	
	
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
- (NSArray *) allData:(NSString *)building 
			room:(NSString *)room{
	// client ip : http://@IP:port/building/room/
	NSString *http = [NSString stringWithFormat:@"http://%1$@:%2$@/%3$@/%4$@", 
												[self savedIp], 
												[self savedPort],
												building,
												room];
	
	ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:[NSURL URLWithString:http]];
	[request setDelegate:self];
	[request startSynchronous];
	
	NSError *error = [request error];
	if (!error) {
		NSString *responseString = [request responseString];
		NSArray *array = [responseString componentsSeparatedByString:@"-"];
		return array;
	} else {
		NSLog(@"error : %@", error);
		return NO;
	}
	
}

// send the GET request to fetch details for a DataType
- (void) allDataFromDatatype:(NSString *)datatype
					building:(NSString *)building
						room:(NSString *)room
				   beginDate:(NSDate *)bDate 
					 endDate:(NSDate *)eDate {
	
	// client ip : http://@IP:port/detail/building/room/dataType/beginDate/endDate/
	NSString *http = [NSString stringWithFormat:@"http://%1$@:%2$@/%3$@/%4$@/%5$@/%$6@/%$7@",
					  [self savedIp], [self savedPort], building, room, datatype, bDate, eDate];
	NSLog(@"url = %@", http);
	NSURL *url = [NSURL URLWithString:http];
	ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:url];
	//[request setDelegate:self];
	[request startSynchronous];
	NSLog(@"response get all: %@", [request responseString]);
	
	[http release];
	[url release];
	[request release];
}


/**
 *	POST Requests
 */

// send the POST request to update a value
- (BOOL) sendPostRequest:(NSString *)value 
				datatype:(NSString *)datatype
				building:(NSString *)building
					room:(NSString *)room
				actuator:(NSString *)actuator {
	
	//if there is a connection between the server and the client 
	if (![self connectionToServer:[self savedIp] portServer:[self savedPort]]) {
		return NO;
	} else {
		ASIFormDataRequest *request = [ASIFormDataRequest requestWithURL:
										[NSURL URLWithString: 
											[NSString stringWithFormat:@"http://%1$@:%2$@", [self savedIp], [self savedPort]]]];
		
		[request addPostValue:[NSString stringWithFormat:@"%@", value] forKey:@"value"];
		[request addPostValue:[NSString stringWithFormat:@"%@", datatype] forKey:@"datatype"];
		[request addPostValue:[NSString stringWithFormat:@"%@", building] forKey:@"building"];
		[request addPostValue:[NSString stringWithFormat:@"%@", room] forKey:@"room"];
		[request addPostValue:[NSString stringWithFormat:@"%@", actuator] forKey:@"actuator"];
		
		[request setDelegate:self];
		[request startSynchronous];
		NSLog(@"command sent!");
		
		[request release];
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
}*/


@end
