package com.appdynamics.eydigital;

import android.util.Log;

import com.appdynamics.eumagent.runtime.CallTracker;
import com.appdynamics.eumagent.runtime.HttpRequestTracker;
import com.appdynamics.eumagent.runtime.Instrumentation;
import com.appdynamics.eumagent.runtime.ServerCorrelationHeaders;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
		} else if(action.equals("beginCall")) {
			String name = args.getString(0);
			String method = args.getString(1);
			// make key from UUID
			String key = UUID.randomUUID().toString();
			HashMap cache = AppDSharedCache.getInstance();
			cache.put(key, Instrumentation.beginCall(name, method));
			cbContext.success(key);
			status = true;
		} else if(action.equals("endCall")) {
			String key = args.getString(0);
			HashMap cache = AppDSharedCache.getInstance();
			CallTracker tracker = (CallTracker)cache.get(key);
			if(tracker != null) {
				Instrumentation.endCall(tracker);
				cache.remove(key);
				status = true;
				cbContext.success();
			}
		} else if(action.equals("leaveBreadcrumb")) {
			String crumb = args.getString(0);
			Instrumentation.leaveBreadcrumb(crumb);
			status = true;
			cbContext.success();
		} else if(action.equals("getCorrelationHeaders")) {
			Map<String, List<String>> headersMap = ServerCorrelationHeaders.generate();
			JSONObject headers = new JSONObject();
			for(Map.Entry<String, List<String>> entry : headersMap.entrySet()) {
				headers.put(entry.getKey(), entry.getValue().get(0));
			}
			cbContext.success(headers);
		} else if(action.equals("beginHttpRequest")) {
			String urlString = args.getString(0);
			try {
				URL url = new URL(urlString);
				HashMap cache = AppDSharedCache.getInstance();
				HttpRequestTracker tracker = Instrumentation.beginHttpRequest(url);
				// make key out of UUID
				String key = UUID.randomUUID().toString();
				cache.put(key, tracker);
				cbContext.success(key);
				status = true;
			} catch(MalformedURLException e) {
				// Log it
				Log.e(TAG, "Exception: " + e.getMessage());
				cbContext.error(e.getMessage());
			}
		} else if(action.equals("reportDone")) {
			String tkey = args.getString(0);
			int responsecode = args.getInt(1);
			JSONObject headersObj = args.getJSONObject(2);

			// Loop through JSON Object
			HashMap headersMap = new HashMap();
			Iterator itor = headersObj.keys();
			while(itor.hasNext()) {
				String key = (String)itor.next();
				String val = headersObj.getString(key);
				ArrayList list = new ArrayList();
				list.add(val);
				// AppD magic headers must be uppercase CORE-39486
				if(key.startsWith("adrum")) {
					key = key.toUpperCase();
				}
				headersMap.put(key, list);
			}
			Log.i(TAG, ">>> ResponseHeaders " + headersMap);
			
			HashMap cache = AppDSharedCache.getInstance();
			HttpRequestTracker tracker = (HttpRequestTracker)cache.get(tkey);
			if(tracker != null) {
				tracker.withResponseHeaderFields(headersMap).withResponseCode(responsecode).reportDone();
				cache.remove(tkey);
				status = true;
				cbContext.success();
			}
		}else {
            if (action.equals("sendResultReport")) {
                JSONArray reporData = args.getJSONArray(0);
                String urlString = args.getString(1);
				String textQualifiers = args.getString(2);
                try {
                    URL url = new URL(urlString);
                    int responsecode = 200;
                    for (int index = reporData.length()-1; index >= 0; index--) {
                        JSONObject report = reporData.getJSONObject(index);
                            String key = report.get("property")+textQualifiers;
                            String value = textQualifiers + report.get("value") + textQualifiers;
                            if (key == null || value == null || key.length() == 0 || value.length() == 0) {
                                cbContext.error("No Information");
                                Log.e(TAG, "No Information");
                            } else {
                                Instrumentation.setUserData(key, value, false);
                            }
                    }
                    HttpRequestTracker tracker = Instrumentation.beginHttpRequest(url);
                    tracker.withResponseHeaderFields(null).withResponseCode(responsecode).reportDone();
                    status = true;
                    cbContext.success();

                } catch (MalformedURLException e) {
                    // Log it
                    Log.e(TAG, "Exception: " + e.getMessage());
                    cbContext.error(e.getMessage());
                }


            } else { // catch all
                Log.e(TAG, "Method not recognised");
                status = false;
                cbContext.error("Method not recognised");
            }
        }
		
		return status;
	}
	
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		// local init code here
	}
}
