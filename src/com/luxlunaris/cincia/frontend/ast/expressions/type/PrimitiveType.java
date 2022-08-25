package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class PrimitiveType extends OneNameType{
	
	public static final Keywords INT = Keywords.INT;
	public static final Keywords FLOAT = Keywords.FLOAT;
	public static  final Keywords BOOL = Keywords.BOOL;
	
	public Keywords value;
	
	public PrimitiveType(Keywords type) {
		value = type;
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
