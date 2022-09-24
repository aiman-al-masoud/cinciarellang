package com.luxlunaris.cincia.frontend.ast.tokens.modifier;

public enum Modifiers {

	PRIVATE, 
	STATIC, 
	FINAL, 
	//	GET, 
	//	SET, 
	//	SINGLETON, 
	//	POOLED, 
	//	CONS,
	//	PURE,
	REF,
	NONLOCAL;

	public static Modifiers fromString(String string) {
		try {
			return valueOf(string.toUpperCase());
		}catch (Exception e) {
			return null;
		}
	}

	public static boolean isModifier(String string) {
		return fromString(string) !=null;
	}

}
