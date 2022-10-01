package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
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

	@Override
	public boolean matches(Type other) {
		
		
		other =other.unwrap();
		
		try {
			return value.equals(((PrimitiveType)other).value);
		}catch (ClassCastException e) {
			
		}
		
		return false;
		
	}
	
	@Override
	public Type unwrap() {
		return this;
	}
	
}
