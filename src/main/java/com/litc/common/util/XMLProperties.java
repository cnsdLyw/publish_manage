package com.litc.common.util;

import java.util.HashMap;
import java.util.Map;

public class XMLProperties {

	private Map<String, String> propertyCache = new HashMap<String, String>();

	public Map<String, String> getPropertyCache() {
		return propertyCache;
	}

	public void setPropertyCache(Map<String, String> propertyCache) {
		this.propertyCache = propertyCache;
	}

	public String getProperty(String proKey) {
		return propertyCache.get(proKey);
	}

	public void setProperty(String name, String value) {
			
	}
}
