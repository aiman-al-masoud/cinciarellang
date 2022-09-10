package com.luxlunaris.cincia.backend.interfaces;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public interface CinciaClass extends CinciaObject, Type, Callable{
	
	CinciaObject newInstance(List<CinciaObject> args);
	
}
