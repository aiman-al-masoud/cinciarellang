package com.luxlunaris.cincia.backend;


import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaObject {

	private boolean immutable;	
	private Enviro enviro; //internal environment of the objects
	Type type; // object's type
	CinciaClass myClass; // object's class
	Object value; //for primitive types


	public CinciaObject(Type type) {
		this.type = type;
		immutable = false;
		set("this", this, type); //TODO: extract into keywords
	}
	
	public CinciaObject(int value) {
		type = new PrimitiveType(PrimitiveType.INT);
		immutable = true;
		this.value = value;
	}
	
	public CinciaObject(double value) {
		type = new PrimitiveType(PrimitiveType.FLOAT);
		immutable = true;
		this.value = value;
	}
	
	public CinciaObject(boolean value) {
		type = new PrimitiveType(PrimitiveType.BOOL);
		immutable = true;
		this.value = value;
	}
	
	
	public CinciaObject(String value) {
		type = new PrimitiveType(PrimitiveType.STRING);
		immutable = true;
		this.value = value;
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
