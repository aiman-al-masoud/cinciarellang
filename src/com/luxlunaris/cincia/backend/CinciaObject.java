package com.luxlunaris.cincia.backend;

import java.util.HashMap;
import java.util.Map;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaObject {

	private boolean immutable;	
	Map<String, Object> attribs;
	Type type;


	public CinciaObject(Type type) {

		attribs = new HashMap<String, Object>();
		this.type = type;
		immutable = false;
	}

	public Object get(String key) {
		return attribs.get(key);
	}

	public void set(String key, Object val) {

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
