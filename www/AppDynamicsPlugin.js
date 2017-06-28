//  AppDynamicsPlugin.h
//  Created by VishwasHK on 22/06/2017.
//
var exec = require('cordova/exec');

var AppDynamicsPlugin = function(){};
AppDynamicsPlugin.prototype.reportMetrics = reportMetric;
AppDynamicsPlugin.prototype.setUserData = setUserData;
AppDynamicsPlugin.prototype.startTrackerWithName = startTrackerWithName;
AppDynamicsPlugin.prototype.stopTrackerWithName = stopTrackerWithName;


function reportMetric (metricName,metricValue,options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','reportMetricWithName',[metricName,metricValue]);
}

function setUserData (userData,options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','setUserData',[userData]);
}

function startTrackerWithName (trackerName,options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','startTimerWithName',[trackerName]);
}

function stopTrackerWithName (trackerName,options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','stopTimerWithName',[trackerName]);
}

module.exports = new AppDynamicsPlugin();