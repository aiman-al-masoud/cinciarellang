package com.luxlunaris.cincia.frontend.ast.interfaces;

import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;

public interface Type extends Expression{
	
	public static Type Any = new IdentifierType("Any");
	public static Type Module = new IdentifierType("Module");

	
	boolean matches(Type other);
	
	
}
