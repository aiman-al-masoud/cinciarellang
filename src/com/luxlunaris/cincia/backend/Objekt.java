package com.luxlunaris.cincia.backend;

import java.util.HashMap;
import java.util.Map;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class Objekt {
	
//	public final String INT = "int";
//	public final String FLOAT = "float";
//	public final String BOOL = "bool";
//	public final String STRING = "string";
	private boolean immutable;
	
//	String type;
	Map<String, Object> attribs;
	Type type;

	
	public Objekt(Type type) {
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
