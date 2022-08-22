package com.luxlunaris.cincia.frontend.ast.tokens.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class SimpleType implements Type{
	
	public Identifier value;
	
	public SimpleType(Identifier value) {
		this.value = value;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
