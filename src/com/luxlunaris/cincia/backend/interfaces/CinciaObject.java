package com.luxlunaris.cincia.backend.interfaces;

import java.util.List;

import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.object.JavaObject;
import com.luxlunaris.cincia.backend.object.Magic;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.primitives.CinciaFloat;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public interface CinciaObject extends Stateful{

	/**
	 * Creates a CinciaObject wrapper from a java object. 
	 * @param object
	 * @return
	 */
	static CinciaObject wrap(Object object) {


		if(object instanceof Boolean) {
			return new CinciaBool((boolean)object);
		}else if(object instanceof String) {
			return new CinciaString((String)object);
		}else if(object instanceof Double || object instanceof Float) {
			return new CinciaFloat((double)object);
		}else if(object instanceof Integer) {
			return new CinciaInt((int)object);
		}else {
			return new JavaObject(object);
		}

	}


	/**
	 * "Unwraps" the Cincia object to obtain a Java-equivalent.
	 * @return
	 */
	Object toJava(); 
	Type getType();
	CinciaObject get(int key);
	CinciaObject get(Magic key);
	CinciaObject get(CinciaObject key);
	CinciaObject get(CinciaIterable key);
	void set(int key, CinciaObject val, Type type);
	void set(int key, CinciaObject val);
	void set(Magic key, CinciaObject val);
	void set(CinciaObject key, CinciaObject val);
	void set(CinciaIterable key, CinciaObject val);
	void setImmutable(); // recursively make the object immutable.
	boolean isImmutable();
	Enviro getEnviro();
	CinciaBool __bool__();
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
	CinciaString __str__() ;
	CinciaObject __neg__() ;
	CinciaObject __init__(List<CinciaObject> args);
	CinciaObject copy(List<CinciaObject> args); // returns a deep copy of the object
	CinciaObject freeze(List<CinciaObject> args);// return an immutable copy of this object
	CinciaObject as(List<CinciaObject> args); //cast/conversion to other class	
	CinciaBool is(List<CinciaObject> args); // in-memory identity for objects

}
