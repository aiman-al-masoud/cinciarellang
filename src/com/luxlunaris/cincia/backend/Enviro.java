package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class Enviro {

	private Enviro parent;
	private Map<String, AbstractCinciaObject> vars;
	private Map<String, Type> types;
	

	public Enviro(Enviro parent) {

		this.parent = parent;

		if(parent.vars != null ) {
			this.vars = new HashMap<String, AbstractCinciaObject>(parent.vars);
			this.types = new HashMap<String, Type>(parent.types);
		}else {
			this.vars = new HashMap<String, AbstractCinciaObject>();
			this.types = new HashMap<String, Type>();
		}

	}

	public Enviro newChild() {
		return new Enviro(this);
	}


	public AbstractCinciaObject get(String key) {
		return vars.get(key);
	}
	
	public Type getType(String key) {
		return types.get(key);
	}
	
	public void set(String key, AbstractCinciaObject val, Type type) {
		vars.put(key, val);
		types.put(key, type);
	}

	public void remove(String key) {
		vars.remove(key);
		types.remove(key);
	}
	
	public Enviro getParent() {
		return parent;
	}
	
	public List<AbstractCinciaObject> values(){
		return new ArrayList<AbstractCinciaObject>(vars.values());
	}


}
