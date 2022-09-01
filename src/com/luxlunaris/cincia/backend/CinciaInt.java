package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

//TODO: implement magic methods
public class CinciaInt extends AbstractCinciaObject {
	
	private int value;

	public CinciaInt(int value) {
		super(new PrimitiveType(PrimitiveType.INT));
		this.value = value;		
	}

}
