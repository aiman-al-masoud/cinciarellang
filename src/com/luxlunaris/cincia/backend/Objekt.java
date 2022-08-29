package com.luxlunaris.cincia.backend;

import java.util.HashMap;
import java.util.Map;

public class Objekt {
	
	String type;
	Map<String, Object> attribs;
	
	public Objekt() {
		attribs = new HashMap<String, Object>();
	}
	
	
	public Object get(String key) {
		return attribs.get(key);
	}


	public void set(String key, Object val) {
		attribs.put(key, val);
	}
	
}
