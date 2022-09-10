package com.luxlunaris.cincia.backend.interfaces;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public interface CinciaClass extends CinciaObject, Type, Callable{
	
	/**
	 * Returns a new instance of the this class, calls the constructor
	 * with the args if possible. 
	 * @param args
	 * @return
	 */
	CinciaObject newInstance(List<CinciaObject> args);
	
}
