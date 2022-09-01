package com.luxlunaris.cincia.backend;


import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.backend.Interpreter.Eval;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

//TODO: subclass for Primitive object
public class CinciaObject {

	private boolean immutable;	
	public Enviro enviro; //object's internal environment 
	Type type; // object's type
	CinciaClass myClass; // for class types
	Object value; //for primitive types


	public CinciaObject(Type type) {
		this.type = type;
		immutable = false;
		enviro = new Enviro(null); //TODO: parent null?
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

	//TODO: hacky fix, read Interpreter.evalCompoundStatement()
	public CinciaObject(Keywords value) {
		type =  null;
		immutable = true;
		this.value = value;
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


	public boolean __bool__(){


		if(type instanceof PrimitiveType && ((PrimitiveType)type).value == PrimitiveType.BOOL) {
			return (boolean)value;
		}

		//retrieve __bool__ from object's attributes and call it 
		CinciaMethod cm = (CinciaMethod)get(Magic.__bool__);
		return (boolean)cm.run(null).value;

	}


	public CinciaObject __add__(CinciaObject other) {

		//TODO: add all cases
		if(type instanceof PrimitiveType && other.type instanceof PrimitiveType) {
			return new CinciaObject((int)value + (int)other.value);
		}

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



}
