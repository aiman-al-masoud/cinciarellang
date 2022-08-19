package com.luxlunaris.cincia.frontend.token;

import javax.management.RuntimeErrorException;

public enum Punctuations {
	
	NEWLINE, 
	SEMI_COL, 
	COL, 
	PAREN_OPN, 
	PAREN_CLS, 
	SQBR_OPN, 
	SQBR_CLS, 
	CURLY_OPN, 
	CURLY_CLS,
	COMMA, 
	DOT, 
	SLASH_BCK;
	
	public static Punctuations fromChar(char value){
		
		switch (value) {
		
			case '\n':
				return NEWLINE;
			case ';':
				return SEMI_COL;
			case ':':
				return COL;
			case '(':
				return PAREN_OPN;
			case ')':
				return PAREN_CLS;
			case '[':
				return SQBR_OPN;
			case ']':
				return SQBR_CLS;
			case '{':
				return CURLY_OPN;
			case '}':
				return CURLY_CLS;
			case ',':
				return COMMA;
			case '.':
				return DOT;
			case '\\':
				return SLASH_BCK;
		}
		
		return null;
		
	}
	
	
	public static boolean isPunctuation(char value) {
		return fromChar(value) != null;
	}
	
	
	
	
	
}
