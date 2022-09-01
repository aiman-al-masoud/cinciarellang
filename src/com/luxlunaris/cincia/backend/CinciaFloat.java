package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaFloat extends CinciaObject {
	
	private double value;

	public CinciaFloat(double value) {
		super(new PrimitiveType(PrimitiveType.FLOAT));
		this.value = value;		
	}

}
