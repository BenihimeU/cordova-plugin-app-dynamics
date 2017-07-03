//  AppDynamicsPlugin.h
//  Created by VishwasHK on 22/06/2017.
//
var exec = require('cordova/exec');

var AppDynamicsPlugin = function(){};
AppDynamicsPlugin.prototype.reportMetrics = reportMetric;
AppDynamicsPlugin.prototype.setUserData = setUserData;
AppDynamicsPlugin.prototype.startTrackerWithName = startTrackerWithName;
AppDynamicsPlugin.prototype.stopTrackerWithName = stopTrackerWithName;
AppDynamicsPlugin.prototype.beginCallWithNameAndAction = beginCallWithNameAndAction;
AppDynamicsPlugin.prototype.endCallwithRefKey = endCallwithRefKey;
AppDynamicsPlugin.prototype.leaveBreadcrumb = leaveBreadcrumb;
AppDynamicsPlugin.prototype.trackHTTPRequestWithURL = trackHTTPRequestWithURL;
AppDynamicsPlugin.prototype.reportDone = reportDone;
AppDynamicsPlugin.prototype.getCorrelationHeaders = getCorrelationHeaders;


function reportMetric (metricName, metricValue, options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','reportMetricWithName',[metricName, metricValue]);
}

function setUserData (property, value, isPersist, options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','setUserData',[property, value, isPersist]);
}

function startTrackerWithName (trackerName, options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','startTimerWithName',[trackerName]);
}

function stopTrackerWithName (trackerName, options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','stopTimerWithName',[trackerName]);
}

function beginCallWithNameAndAction (callName, action, options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','beginCall',[callName, action]);
}

function endCallwithRefKey (refKey, options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','endCall',[refKey]);
}

function leaveBreadcrumb (crumb, options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','leaveBreadcrumb',[crumb]);
}

function trackHTTPRequestWithURL (url, options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','beginHttpRequest',[url]);
}

function reportDone (key, status, options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','reportDone',[key, status]);
}

function getCorrelationHeaders (options) {
	options = options || {};
	exec(options.success || null,options.error || null, 'AppDynamicsPlugin','getCorrelationHeaders',[]);
}

module.exports = new AppDynamicsPlugin();