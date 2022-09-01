package com.luxlunaris.cincia.frontend.ast.expressions.type;

public class IdentifierType extends OneNameType{
	
	public String value;
	
	public IdentifierType(String id) {
		this.value = id;
	}
	
	@Override
	public String toString() {
		return value+"";
	}

}
