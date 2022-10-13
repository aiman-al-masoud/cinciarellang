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

		//TODO: problem with classes defining static members of the same class
		//TODO: CinciaInt MUST ALSO SUPPORT LOOOOONG

		if(object instanceof Boolean) {
			return new CinciaBool((boolean)object);
		}else if(object instanceof String) {
			return new CinciaString((String)object);
		}else if(object instanceof Double || object instanceof Float) {
			return new CinciaFloat((double)object);
		}else if(object instanceof Integer || object instanceof Long || object instanceof Short) {
			return new CinciaInt(object instanceof Long ? Math.toIntExact((Long)object) : (int)object);
		}else {
			return new JavaObject(object);
		}

	}


	/**
	 * "Unwraps" the CinciaObject to get back a Java equivalent.
	 * @return
	 */
	Object toJava(); 
	
	/**
	 * Like {@link #toJava()}, but it takes in extra arguments needed for 
	 * the conversion of a CinciaObject to a proper Java equivalent.
	 * For example, the arg may be a Java functional interface or class.
	 * @return
	 */
	Object toJava(Object... args);
	
	Type getType();
	
	/**
	 * Recursively make this object, and all of its children objects, immutable.
	 * Inaccessible from cincia, see {@link #freeze(List)} instead, which returns an
	 * immutable copy.
	 */
	void setImmutable();
	
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
	CinciaBool __eq__(CinciaObject other) ;
	CinciaBool __ne__(CinciaObject other);
	CinciaObject __not__();
	CinciaString __str__();
	CinciaObject __neg__();
	CinciaObject __init__(List<CinciaObject> args);
	
	/**
	 * Returns a deep (recursive or bitwise) copy of the object
	 * @param args
	 * @return
	 */
	CinciaObject copy(List<CinciaObject> args); 
	
	/**
	 * Like {@link #copy(List)}, but the copy is also immutable.
	 * @param args
	 * @return
	 */
	CinciaObject freeze(List<CinciaObject> args);
	
	
	CinciaObject as(List<CinciaObject> args);
	
	/**
	 * Checks if this object and the other are identical in memory, unoverridable.
	 * For (logical) equality see  {@link #__eq__(List)}.
	 * @param args
	 * @return
	 */
	CinciaBool is(List<CinciaObject> args); 
	
	CinciaString help(List<CinciaObject> args);
	void setDocstring(String docstring); // changes object's docstring only if it was null

}
