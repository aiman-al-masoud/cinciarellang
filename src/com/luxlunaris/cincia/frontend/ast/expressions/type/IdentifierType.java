package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

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
