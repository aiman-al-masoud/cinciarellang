package com.luxlunaris.cincia.frontend.ast.tokens;

import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;

public class Identifier extends AbstractToken implements LeftValue{
	
	public final String value;
	
	public Identifier(String value) {
		this.value = value;
	}
	
	public static boolean isIdStart(char c){
		return  Character.isLetter(c) || c == '_';
	}

	public static boolean isId(char c){
		return isIdStart(c) || Character.isDigit(c);
	}
	
	
	
}
