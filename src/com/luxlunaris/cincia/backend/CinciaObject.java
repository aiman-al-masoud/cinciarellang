package com.luxlunaris.cincia.backend;


import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaObject {

	private boolean immutable;	
	private Enviro enviro; //internal environment of the objects
	Type type; // object's type
	CinciaClass myClass; // object's class


	public CinciaObject(Type type) {

		this.type = type;
		immutable = false;
		set("this", this, type); //TODO: extract into keywords
	}

	public CinciaObject get(String key) {
		return enviro.get(key);
	}
	
	public Type getType(String key) {
		return enviro.getType(key);
	}

	public void set(String key, CinciaObject val, Type type) {

		if(!immutable) {
			enviro.set(key, val, type);
		}else {
			throw new RuntimeException("Cannot mutate immutable object!");
		}
	}

	public void remove(String key) {
		
		if(!immutable) {
			enviro.remove(key);
		}else {
			throw new RuntimeException("Cannot mutate immutable object!");
		}
	}

	public void setImmutable() {
		immutable = true;
	}
	
	public Enviro getEnviro() {
		return enviro;
	}
	


}
