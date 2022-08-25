package com.luxlunaris.cincia.frontend.ast.tokens.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class IdentifierType extends OneNameType{
	
	public Identifier value;
	
	public IdentifierType(Identifier value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value+"";
	}

//	@Override
//	public Object getValue() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
