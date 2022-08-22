package com.luxlunaris.cincia.frontend.ast.tokens.type;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

//TODO: add in EBNF
public class UnionType implements Type{
	
	public List<Type> types;
	
	public UnionType(List<Type> types) {
		this.types = types;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
