package com.luxlunaris.cincia.frontend.ast.tokens.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class PrimitiveType implements Type{
	
	public static final int INT = 0;
	public static final int FLOAT = 1;
	public static  final int BOOL = 2;
	
	public int value;
	
	public PrimitiveType(int type) {
		value = type;
	}
	

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
