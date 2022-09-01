package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

public class CinciaString extends AbstractCinciaObject {

	private String value;

	public CinciaString(String value) {
		super(new PrimitiveType(PrimitiveType.STRING));
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}
	
	@Override
	public AbstractCinciaObject __add__(AbstractCinciaObject other) {
		
		try {
			CinciaString otherStr =  (CinciaString)other;
			return new CinciaString(value+otherStr.value);
		} catch (ClassCastException e) {
		
		}
		
		throw new RuntimeException("Operator + undefined ...");
	}


}
