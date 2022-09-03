package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

public class CinciaString extends PrimitiveCinciaObject {

	private String value;

	public CinciaString(String value) {
		super(new PrimitiveType(PrimitiveType.STRING));
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public CinciaObject __add__(CinciaObject other) {
		
		try {
			CinciaString otherStr =  (CinciaString)other;
			return new CinciaString(value+otherStr.value);
		} catch (ClassCastException e) {
		
		}
		
		throw new RuntimeException("Operator + undefined ...");
	}
	
	@Override
	public String toString() {
		return "\""+value+"\"";
	}


}
