//
//  ConnectionManager.m
//  nsoc
//
//  Created by Pierre BARON on 25/01/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

/*
 * ConnectionManagaer is the class to use if you want to send or receive data from the server.
 */

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

// get the saved IP address in the iPhone database
- (NSString *) savedIp {
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	savedIp = [defaults objectForKey:@"savedIp"];
	
	if(savedIp == nil){
		savedIp = @"192.168.1.1";
	}
	
	return savedIp;	
}

// get the saved port in the iPhone database
- (NSString *) savedPort {
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	savedPort = [defaults objectForKey:@"savedPort"];
	
	if(savedPort == nil){
		savedPort = @"8182";
	}
	
	return savedPort;
}

// save the new IP address in the iPhone database
- (void) setSavedIp:(NSString *) ip{
	NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
	[defaults setObject:ip forKey:@"savedIp"];
    [defaults synchronize];	
	
	savedIp = ip;
}

// save the new port in the iPhone database
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
		
	// we create the http string
	NSString *http = [NSString stringWithFormat:@"http://%1$@:%2$@", ip, port];
	NSURL *url = [NSURL URLWithString:http];
	
	// we send the request to the server
	ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:url];
	[request setDelegate:self];
	/*
	 * FUTURE IMPROVEMENT
	 * send asynchronous requests instead of synchronous
	 */
	
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
				 room:(NSString *)room {
	
	// show loader in the status bar
	[UIApplication sharedApplication].networkActivityIndicatorVisible = YES;

	NSLog(@"GET request sent!");

	// here is the http request to build to fetch all data
	// client ip : http://@IP:port/building/room/
	NSString *http = [NSString stringWithFormat:@"http://%1$@:%2$@/%3$@/%4$@", 
												[self savedIp], 
												[self savedPort],
												building,
												room];
	
	// we send the request
	ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:[NSURL URLWithString:http]];
	[request setDelegate:self];
	[request startSynchronous];
	
	// we are looking for error
	NSError *error = [request error];
	if (!error) {
		// we fetch the big String from the server
		// we have to parse it.
		
		/*
		 * FUTURE IMPROVEMENT
		 * use JSON to send key:values instead of a String
		 */
		NSString *responseString = [request responseString];
		NSArray *array = [responseString componentsSeparatedByString:@"-"];
		return array;
	} else {
		NSLog(@"error : %@", error);
		return NO;
	}
	
}

// send the GET request to fetch details for a DataType
/*
 * METHOD NOT TESTED
 */
- (void) allDataFromDatatype:(NSString *)datatype
					building:(NSString *)building
						room:(NSString *)room
				   beginDate:(NSDate *)bDate 
					 endDate:(NSDate *)eDate {
	
	// here is the http request to build to fetch all data from a datatype
	// client ip : http://@IP:port/detail/building/room/dataType/beginDate/endDate/
	
	
	NSString *http = [NSString stringWithFormat:@"http://%1$@:%2$@/%3$@/%4$@/%5$@/%$6@/%$7@",
					  [self savedIp], [self savedPort], building, room, datatype, bDate, eDate];
	NSLog(@"url = %@", http);
	NSURL *url = [NSURL URLWithString:http];
	
	ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:url];
	[request setDelegate:self];
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
- (void) sendPostRequest:(NSString *)value 
				datatype:(NSString *)datatype
				building:(NSString *)building
					room:(NSString *)room
				actuator:(NSString *)actuator {
	[UIApplication sharedApplication].networkActivityIndicatorVisible = YES;

	NSLog(@"POST request sent!");

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
	
	[request release];
}

@end
