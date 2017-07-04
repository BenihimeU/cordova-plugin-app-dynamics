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

- (void)beginCall:(CDVInvokedUrlCommand*)command;
- (void)endCall:(CDVInvokedUrlCommand*)command;

- (void)leaveBreadcrumb:(CDVInvokedUrlCommand*)command;
- (void)beginHttpRequest:(CDVInvokedUrlCommand*)command;
- (void)reportDone:(CDVInvokedUrlCommand*)command;
- (void)getCorrelationHeaders:(CDVInvokedUrlCommand*)command;

-(void)sendResultReport:(CDVInvokedUrlCommand*)command;

@end
