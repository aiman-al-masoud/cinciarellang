package com.luxlunaris.cincia.backend.primitives;

import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

//TODO: better error messages
public class CinciaFloat extends CinciaNumber {

	protected double value;

	public CinciaFloat(double value) {
		super(new TypeWrapper(new PrimitiveType(PrimitiveType.FLOAT)));
		this.value = value;		
	}
	
	@Override
	public Double toJava() {
		return value;
	}

}
