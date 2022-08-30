package com.luxlunaris.cincia.backend;

import java.util.HashMap;
import java.util.Map;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaObject {

	private boolean immutable;	
	private Map<String, CinciaObject> attribs;
	Type type;


	public CinciaObject(Type type) {

		attribs = new HashMap<String, CinciaObject>();
		this.type = type;
		immutable = false;
	}

	public CinciaObject get(String key) {
		return attribs.get(key);
	}

	public void set(String key, CinciaObject val) {

		if(!immutable) {
			attribs.put(key, val);
		}else {
			throw new RuntimeException("Cannot mutate immutable object!");
		}
	}

	public void remove(String key) {
		
		if(!immutable) {
			attribs.remove(key);
		}else {
			throw new RuntimeException("Cannot mutate immutable object!");
		}
	}

	public void setImmutable() {
		immutable = true;
	}



}
