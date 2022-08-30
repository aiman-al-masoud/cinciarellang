package com.luxlunaris.cincia.backend;

import java.util.HashMap;
import java.util.Map;

public class Enviro {

	private Enviro parent;
	private Map<String, CinciaObject> vars;


	public Enviro(Enviro parent) {

		this.parent = parent;

		if(parent.vars != null ) {
			this.vars = new HashMap<String, CinciaObject>(parent.vars);
		}else {
			this.vars = new HashMap<String, CinciaObject>();
		}

	}

	public Enviro newChild() {
		return new Enviro(this);
	}


	public CinciaObject get(String key) {
		return vars.get(key);
	}
	

	public void set(String key, CinciaObject val) {
		vars.put(key, val);
	}


	public void remove(String key) {
		vars.remove(key);
	}
	
	public Enviro getParent() {
		return parent;
	}







}
