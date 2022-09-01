package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

public class CinciaString extends CinciaObject {

	private String value;

	public CinciaString(String value) {
		super(new PrimitiveType(PrimitiveType.STRING));
		this.value = value;
	}

	
	
	
	



}
