//
//  RKObjectRelationshipMapping.m
//  RestKit
//
//  Created by Blake Watters on 5/4/11.
//  Copyright 2011 Two Toasters. All rights reserved.
//

#import "RKObjectRelationshipMapping.h"

@implementation RKObjectRelationshipMapping

@synthesize mapping = _mapping;
@synthesize reversible = _reversible;

+ (RKObjectRelationshipMapping*)mappingFromKeyPath:(NSString*)sourceKeyPath toKeyPath:(NSString*)destinationKeyPath withMapping:(id<RKObjectMappingDefinition>)objectOrDynamicMapping reversible:(BOOL)reversible {
    RKObjectRelationshipMapping* relationshipMapping = (RKObjectRelationshipMapping*) [self mappingFromKeyPath:sourceKeyPath toKeyPath:destinationKeyPath];    
    relationshipMapping.reversible = reversible;
    relationshipMapping.mapping = objectOrDynamicMapping;
    return relationshipMapping;
}

+ (RKObjectRelationshipMapping*)mappingFromKeyPath:(NSString*)sourceKeyPath toKeyPath:(NSString*)destinationKeyPath withMapping:(id<RKObjectMappingDefinition>)objectOrDynamicMapping {
    return [self mappingFromKeyPath:sourceKeyPath toKeyPath:destinationKeyPath withMapping:objectOrDynamicMapping reversible:YES];
}

- (id)copyWithZone:(NSZone *)zone {
    RKObjectRelationshipMapping* copy = [super copyWithZone:zone];
    copy.mapping = self.mapping;
    copy.reversible = self.reversible;
    return copy;
}

- (void)dealloc {
    [_mapping release];
    [super dealloc];
}

@end
