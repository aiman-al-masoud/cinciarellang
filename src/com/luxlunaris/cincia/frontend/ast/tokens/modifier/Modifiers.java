package com.luxlunaris.cincia.frontend.ast.tokens.modifier;

import java.util.Arrays;

public enum Modifiers {

	PRIVATE, 
	STATIC, 
	FINAL, 
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
	
	public static boolean isAssignmentModifier(Modifiers modifier) {
		return Arrays.asList( Modifiers.FINAL, Modifiers.STATIC, Modifiers.PRIVATE ).contains(modifier);
	}

}
