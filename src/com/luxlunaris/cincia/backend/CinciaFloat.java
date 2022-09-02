package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

//TODO: implement magic methods
public class CinciaFloat extends AbstractCinciaObject {
	
	private double value;

	public CinciaFloat(double value) {
		super(new PrimitiveType(PrimitiveType.FLOAT));
		this.value = value;		
	}
	
	@Override
	public Double getValue() {
		return value;
	}

}
