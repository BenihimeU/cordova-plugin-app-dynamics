//  AppDynamicsPlugin.h
//  Created by VishwasHK on 22/06/2017.
//

#import <Cordova/CDVPlugin.h>
#import <ADEUMInstrumentation/ADEUMInstrumentation.h>

@interface AppDynamicsPlugin : CDVPlugin

- (void)reportMetricWithName:(CDVInvokedUrlCommand*)command;
- (void)setUserData:(CDVInvokedUrlCommand*)command;

- (void)startTimerWithName:(CDVInvokedUrlCommand*)command;
- (void)stopTimerWithName:(CDVInvokedUrlCommand*)command;

@end
