//
//  RKRequeteClient.h
//  chenillard
//
//  Created by Pierre BARON on 06/04/11.
//  Copyright 2011 Pierre Baron. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <RestKit/RestKit.h>


@interface RKRequeteClient : NSObject<RKRequestDelegate>{

}

- (void) sendGetRequest;
- (void) sendPostRequest: (NSDictionary*) dictionnary;
- (void) request:(RKRequest*) request didLoadResponse:(RKResponse*) response;

@end
