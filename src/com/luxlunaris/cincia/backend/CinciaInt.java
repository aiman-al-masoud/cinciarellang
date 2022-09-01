package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

public class CinciaInt extends CinciaObject {
	
	private int value;

	public CinciaInt(int value) {
		super(new PrimitiveType(PrimitiveType.INT));
		this.value = value;		
	}

}
