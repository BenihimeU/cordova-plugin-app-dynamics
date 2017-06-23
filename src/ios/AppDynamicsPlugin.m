//  AppDynamicsPlugin.m
//  Created by VishwasHK on 22/06/2017.
//


#import "AppDynamicsPlugin.h"

@implementation AppDynamicsPlugin

- (void)reportMetricWithName:(CDVInvokedUrlCommand*)command
{
	NSLog(@"ReportingMetricName");
    CDVPluginResult* pluginResult = nil;
    NSString* name = [command.arguments objectAtIndex:0];
    NSInteger value = [[command.arguments objectAtIndex:1] integerValue];
    
    NSLog(@"Metric %@ %li", name, (long)value);
    
    if(name != nil && [name length] > 0) {
        [ADEumInstrumentation reportMetricWithName:name value:value];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)startTimerWithName:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* name = [command.arguments objectAtIndex:0];
    
    NSLog(@"Start timer %@", name);
    
    if(name != nil && [name length] > 0) {
        [ADEumInstrumentation startTimerWithName:name];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)stopTimerWithName:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* name = [command.arguments objectAtIndex:0];
    
    NSLog(@"Stop timer %@", name);
    
    if(name != nil && [name length] > 0) {
        [ADEumInstrumentation stopTimerWithName:name];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setUserData:(CDVInvokedUrlCommand*)command
{
	NSLog(@"Set Userdata");
    CDVPluginResult* pluginResult = nil;
    NSString* key = [command.arguments objectAtIndex:0];
    NSString* value = [command.arguments objectAtIndex:1];
    NSString* persistStr = [command.arguments objectAtIndex:2];
    BOOL persist = [persistStr boolValue];
    NSLog(@"user data %@ %@ %i", key, value, persist);
    
    if(key != nil && [key length] > 0 && value != nil && [value length] > 0) {
        [ADEumInstrumentation setUserData:key value:value persist:persist];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
