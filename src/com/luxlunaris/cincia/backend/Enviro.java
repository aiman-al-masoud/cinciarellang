package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class Enviro {

	private Enviro parent;
	private Map<String, CinciaObject> vars;
	private Map<String, Type> types;
	

	public Enviro(Enviro parent) {

		this.parent = parent;

		//TODO: deep not shallow copy!
		if(parent != null ) {
			this.vars = new HashMap<String, CinciaObject>(parent.vars);
			this.types = new HashMap<String, Type>(parent.types);
			
		}else {
			this.vars = new HashMap<String, CinciaObject>();
			this.types = new HashMap<String, Type>();
		}

	}

	public Enviro newChild() {
		return new Enviro(this);
	}


	public CinciaObject get(String key) {
		return vars.get(key);
	}
	
	public Type getType(String key) {
		return types.get(key);
	}
	
	public void set(String key, CinciaObject val, Type type) {
		vars.put(key, val);
		types.put(key, type);
	}
	
	
	public void set(String key, CinciaObject val) {
		set(key, val, val.getType());
	}

	public void remove(String key) {
		vars.remove(key);
		types.remove(key);
	}
	
	public Enviro getParent() {
		return parent;
	}
	
	public List<CinciaObject> values(){
		return new ArrayList<CinciaObject>(vars.values());
//		return vars.values().;
	}
	
	public List<Map.Entry<String, CinciaObject>> items(){
		return new ArrayList<Map.Entry<String,CinciaObject>>(vars.entrySet());
	}


}
