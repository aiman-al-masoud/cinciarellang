package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.ListType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaList extends AbstractCinciaObject {

	public CinciaList(Type type) {
		super(new ListType(type));
	}
	
	
	

}
