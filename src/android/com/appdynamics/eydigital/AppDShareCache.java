package com.appdynamics.eydigital;

import java.util.HashMap;


class AppDSharedCache {
	private static HashMap cache = null;

	public static HashMap getInstance() {
		if(cache == null) {
			cache = new HashMap();
		}

		return cache;
	}
}