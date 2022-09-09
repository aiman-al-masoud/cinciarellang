package com.luxlunaris.cincia.backend.interfaces;

import java.util.List;

import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.object.Magic;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.primitives.CinciaFloat;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public interface CinciaObject {


	static CinciaObject create(Object object) {


		if(object instanceof Boolean) {
			return new CinciaBool((boolean)object);
		}else if(object instanceof String) {
			return new CinciaString((String)object);
		}else if(object instanceof Double) {
			return new CinciaFloat((double)object);
		}else if(object instanceof Integer) {
			return new CinciaInt((int)object);
		}

		throw new RuntimeException("Unknown CinciaObject type wrapper");	
	}

	Object getValue();
	Type getType();
	CinciaObject get(String key);
	CinciaObject get(int key);
	CinciaObject get(Magic key);
	Type getType(String key);
	void set(String key, CinciaObject val, Type type);
	void set(int key, CinciaObject val, Type type);
	void set(String key, CinciaObject val);
	void set(int key, CinciaObject val);
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
	CinciaObject into(List<CinciaObject> args); //cast/conversion to other class	
	CinciaObject copy(List<CinciaObject> args); 
	CinciaObject freeze(List<CinciaObject> args);// return an immutable copy of this object

}