package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

//TODO: add in EBNF
public class DictType extends CollectionType{
	
	public Type keyType;
	public Type valType;
	
//	@Override
//	public Object getValue() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
