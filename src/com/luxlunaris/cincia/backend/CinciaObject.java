package com.luxlunaris.cincia.backend;

import java.util.List;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

interface CinciaObject {


	static AbstractCinciaObject create(Object object) {

		if(object instanceof Boolean) {
			return new CinciaBool((boolean)object);
		}else if(object instanceof String) {
			new CinciaString((String)object);
		}else if(object instanceof Float) {
			return new CinciaFloat((double)object);
		}else if(object instanceof Integer) {
			return new CinciaInt((int)object);
		}

		throw new RuntimeException("Unknown CinciaObject type wrapper");	
	}

	Object getValue();
	AbstractCinciaObject get(String key);
	AbstractCinciaObject get(Magic key);
	Type getType(String key);
	void set(String key, AbstractCinciaObject val, Type type);
	void set(String key, AbstractCinciaObject val);
	void set(Magic key, AbstractCinciaObject val);
	void remove(String key);
	void setImmutable(); // recursively make the object immutable.
	Enviro getEnviro();
	boolean __bool__();
	AbstractCinciaObject __add__(AbstractCinciaObject other);
	AbstractCinciaObject __sub__(AbstractCinciaObject other);
	AbstractCinciaObject __mul__(AbstractCinciaObject other);
	AbstractCinciaObject __mod__(AbstractCinciaObject other);
	AbstractCinciaObject __div__(AbstractCinciaObject other);
	AbstractCinciaObject __or__(AbstractCinciaObject other) ;
	AbstractCinciaObject __and__(AbstractCinciaObject other) ;
	AbstractCinciaObject __lt__(AbstractCinciaObject other) ;
	AbstractCinciaObject __gt__(AbstractCinciaObject other);
	AbstractCinciaObject __lte__(AbstractCinciaObject other);
	AbstractCinciaObject __gte__(AbstractCinciaObject other);
	AbstractCinciaObject __eq__(AbstractCinciaObject other) ;
	AbstractCinciaObject __ne__(AbstractCinciaObject other) ;
	AbstractCinciaObject __not__() ;
	AbstractCinciaObject __str__() ;
	AbstractCinciaObject __neg__() ;
	AbstractCinciaObject __init__(List<AbstractCinciaObject> args);
	AbstractCinciaObject as(List<AbstractCinciaObject> args); //cast/conversion to other class	
	AbstractCinciaObject copy(List<AbstractCinciaObject> args); // return a deep (I believe) copy of this object
	AbstractCinciaObject freeze(List<AbstractCinciaObject> args);// return an immutable copy of this object


}
