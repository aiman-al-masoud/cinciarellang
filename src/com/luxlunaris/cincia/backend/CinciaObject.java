package com.luxlunaris.cincia.backend;


import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

//TODO: subclass for Primitive object
public class CinciaObject implements Cincia{

	private boolean immutable;	
	public Enviro enviro; //object's internal environment 
	Type type; // object's type
	CinciaClass myClass; // for class types

	public CinciaObject(Type type) {
		this.type = type;
		immutable = false;
		enviro = new Enviro(null); //TODO: parent null?
		set("this", this, type); //TODO: extract into keywords
		set(Magic.copy, new CinciaMethod(this::copy));
		set(Magic.freeze, new CinciaMethod(this::freeze));
		//TODO .as()
	}

	public CinciaObject get(String key) {
		return enviro.get(key);
	}

	public CinciaObject get(Magic key) {
		return get(key.toString());
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
	
	public void set(String key, CinciaObject val) {
		set(key, val, val.type);
	}
	
	public void set(Magic key, CinciaObject val) {
		set(key.toString(), val, val.type);
	}


	public void remove(String key) {

		if(!immutable) {
			enviro.remove(key);
		}else {
			throw new RuntimeException("Cannot mutate immutable object!");
		}
	}

	// recursively make the object immutable.
	public void setImmutable() {

		immutable = true;
		
		if(enviro.values().size()==0) {//TODO: maybe unnecessary?
			return;
		}

		enviro.values().stream().forEach(o->{
			if(o!=null) {
				o.setImmutable();
			}
		});

	}

	public Enviro getEnviro() {
		return enviro;
	}


	public boolean __bool__(){


//		if(type instanceof PrimitiveType && ((PrimitiveType)type).value == PrimitiveType.BOOL) {
//			return (boolean)value;
//		}

		//retrieve __bool__ from object's attributes and call it 
		CinciaMethod cm = (CinciaMethod)get(Magic.__bool__);
		return (boolean)cm.run(null).value;

	}


	public CinciaObject __add__(CinciaObject other) {

//		//TODO: add all cases
//		if(type instanceof PrimitiveType && other.type instanceof PrimitiveType) {
//			return new CinciaObject((int)value + (int)other.value);
//		}

		// retrieve __add__ from object's attributes and call it 
		CinciaMethod cm = (CinciaMethod)get(Magic.__add__);
		return cm.run(Arrays.asList(other));

	}


	public CinciaObject __sub__(CinciaObject other) {

	}

	public CinciaObject __mul__(CinciaObject other) {

	}

	public CinciaObject __mod__(CinciaObject other) {

	}

	public CinciaObject __div__(CinciaObject other) {

	}

	public CinciaObject __neg__() {

	}

	public CinciaObject __or__(CinciaObject other) {

	}

	public CinciaObject __and__(CinciaObject other) {

	}

	public CinciaObject __lt__(CinciaObject other) {

	}

	public CinciaObject __gt__(CinciaObject other) {

	}

	public CinciaObject __lte__(CinciaObject other) {

	}

	public CinciaObject __gte__(CinciaObject other) {

	}

	public CinciaObject __eq__(CinciaObject other) {

	}

	public CinciaObject __ne__(CinciaObject other) {

	}

	public CinciaObject __not__() {

	}

	public CinciaObject __str__() {

	}

	public CinciaObject __init__(List<CinciaObject> args) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__init__);
		return cm.run(args);
	}



	// .as(ClassName) //TODO: cast/conversion to other class
	public CinciaObject as(CinciaClass clazz) {
		CinciaMethod cm = (CinciaMethod)get(Magic.as);

	}
	
	// return a deep (I believe) copy of this object
	public CinciaObject copy(List<CinciaObject> args) {
		CinciaObject obj = new CinciaObject(this.type);
		obj.enviro = new Enviro(this.enviro);
		return obj;
	}

	// return an immutable copy of this object
	public CinciaObject freeze(List<CinciaObject> args) {
		CinciaObject o = copy(args);
		o.setImmutable();
		return o;
	}


	@Override
	public Object getValue() {
		return this;
	}


}
