package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class PrimitiveType extends OneNameType{
	
	public static final Keywords INT = Keywords.INT;
	public static final Keywords FLOAT = Keywords.FLOAT;
	public static  final Keywords BOOL = Keywords.BOOL;
	public static  final Keywords STRING = Keywords.STRING;
	
	public Keywords value;
	
	public PrimitiveType(Keywords type) {
		value = type;
	}
	
	@Override
	public String toString() {
		return value+"";
	}
	
}
