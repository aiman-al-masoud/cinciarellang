package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

//TODO: add in EBNF
public class ListType  extends CollectionType{
	
	public Type value;
	
	@Override
	public String toString() {
		return value+"[]";
	}

}
