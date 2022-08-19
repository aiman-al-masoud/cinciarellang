package com.luxlunaris.cincia.frontend.tokens;

public enum Modifiers {

	PRIVATE, 
	STATIC, 
	FINAL, 
	GET, 
	SET, 
	SINGLETON, 
	POOLED, 
	IMMUTABLE,
	CONS;

	public static Modifiers fromString(String string) {
		try {
			return valueOf(string.toUpperCase());
		}catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
	}


	public static boolean isModifier(String string) {
		return fromString(string) !=null;
	}




}
