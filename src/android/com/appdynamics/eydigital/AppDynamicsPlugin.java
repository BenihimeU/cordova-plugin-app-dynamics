package com.appdynamics.eydigital;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import com.appdynamics.eumagent.runtime.Instrumentation;

public class AppDynamicsPlugin extends CordovaPlugin {
	private static final String TAG = "AppDynamicsPlugin";

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext cbContext) throws JSONException {
		boolean status = false;
		
		Log.i(TAG, "Method: " + action);
		Log.i(TAG, "Params: " + args);
		
		if(action.equals("reportMetricWithName")){
			String name = args.getString(0);
			long value = args.getLong(1);
			Instrumentation.reportMetric(name, value);
			status = true;
			cbContext.success();
		} else if(action.equals("startTimerWithName")) {
			String name = args.getString(0);
			Instrumentation.startTimer(name);
			status = true;
			cbContext.success();
		} else if(action.equals("stopTimerWithName")) {
			String name = args.getString(0);
			Instrumentation.stopTimer(name);
			status = true;
			cbContext.success();
		} else if(action.equals("setUserData")) {
			String key = args.getString(0);
			String value = args.getString(1);
			boolean flag = args.getBoolean(2);
			if(key == null || value == null || key.length() == 0 || value.length() == 0) {
				cbContext.error("No Information");
				Log.e(TAG, "No Information");
			} else {
				Instrumentation.setUserData(key, value, flag);
				cbContext.success();
				status = true;
			}
		} else { // catch all
			Log.e(TAG, "Method not recognised");
			status = false;
			cbContext.error("Method not recognised");
		}
		
		return status;
	}
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		// local init code here
	}
}
