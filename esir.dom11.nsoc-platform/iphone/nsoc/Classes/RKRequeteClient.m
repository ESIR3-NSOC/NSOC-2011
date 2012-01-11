//
//  RKRequeteClient.m
//  chenillard
//
//  Created by Pierre BARON on 06/04/11.
//  Copyright 2011 Pierre Baron. All rights reserved.
//

#import "RKRequeteClient.h"

@implementation RKRequeteClient



//Envoi de la requete POST
- (void) sendPostRequest: (NSDictionary*) dictionary{		
	// Add the view controller's view to the window and display.	
	[[RKClient sharedClient] post:@"" params:dictionary delegate:self];
}

//Envoi de la requete GET
- (void) sendGetRequest {
	[[RKClient sharedClient] get:@"" delegate:self];
}

//Affichage des reponses 
- (void) request:(RKRequest*) request didLoadResponse:(RKResponse*) response{
	if ([request isGET]) {  
		NSString *responseString = [response bodyAsString];
		NSLog(@"responseString: %@", responseString);
		if([responseString isEqualToString:@"Connexion Client/Serveur etablie"]){
			UIAlertView *message = [[UIAlertView alloc] initWithTitle: @"Information"  
															  message: @"Connexion etablie"  
															 delegate: nil  
													cancelButtonTitle: @"OK"  
													otherButtonTitles: nil];  
			
			[message show];
			[message release];
		}
		else{
			UIAlertView *message = [[UIAlertView alloc] initWithTitle: @"Information"  
															  message: @"Probleme connexion"  
															 delegate: nil  
													cancelButtonTitle: @"OK"  
													otherButtonTitles: nil]; 
			[message show];
			[message release];
		}
		 
		NSLog(@"%@", [response bodyAsString]);
	}
}

@end
