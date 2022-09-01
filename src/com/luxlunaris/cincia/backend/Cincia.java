package com.luxlunaris.cincia.backend;

import java.util.List;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

interface Cincia {


	static CinciaObject create(Object object) {

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
	CinciaObject get(String key);
	CinciaObject get(Magic key);
	Type getType(String key);
	void set(String key, CinciaObject val, Type type);
	void set(String key, CinciaObject val);
	void set(Magic key, CinciaObject val);
	void remove(String key);
	void setImmutable(); // recursively make the object immutable.
	Enviro getEnviro();
	boolean __bool__();
	CinciaObject __add__(CinciaObject other);
	CinciaObject __sub__(CinciaObject other);
	CinciaObject __mul__(CinciaObject other);
	CinciaObject __mod__(CinciaObject other);
	CinciaObject __div__(CinciaObject other);
	CinciaObject __or__(CinciaObject other) ;
	CinciaObject __and__(CinciaObject other) ;
	CinciaObject __lt__(CinciaObject other) ;
	CinciaObject __gt__(CinciaObject other);
	CinciaObject __lte__(CinciaObject other);
	CinciaObject __gte__(CinciaObject other);
	CinciaObject __eq__(CinciaObject other) ;
	CinciaObject __ne__(CinciaObject other) ;
	CinciaObject __not__() ;
	CinciaObject __str__() ;
	CinciaObject __neg__() ;
	CinciaObject __init__(List<CinciaObject> args);
	CinciaObject as(CinciaClass clazz); //cast/conversion to other class	
	CinciaObject copy(List<CinciaObject> args); // return a deep (I believe) copy of this object
	CinciaObject freeze(List<CinciaObject> args);// return an immutable copy of this object


}
