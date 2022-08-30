package com.luxlunaris.cincia.backend;

import java.util.HashMap;
import java.util.Map;

public class Enviro {

	Enviro parent;
	Map<String, Objekt> vars;


	public Enviro(Enviro parent) {

		this.parent = parent;

		if(parent.vars != null ) {
			this.vars = new HashMap<String, Objekt>(parent.vars);
		}else {
			this.vars = new HashMap<String, Objekt>();
		}

	}

	public Enviro newChild() {
		return new Enviro(this);
	}


	public Object get(String key) {
		return vars.get(key);
	}
	

	public void set(String key, Objekt val) {
		vars.put(key, val);
	}


	public void remove(String key) {
		vars.remove(key);
	}








}
