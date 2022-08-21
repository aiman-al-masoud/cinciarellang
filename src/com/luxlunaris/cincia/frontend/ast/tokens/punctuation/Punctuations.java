package com.luxlunaris.cincia.frontend.ast.tokens.punctuation;

import javax.management.RuntimeErrorException;

public enum Punctuations {
	
//	NEWLINE, 
//	SEMI_COL,
	STM_SEP, //statement separator, newline or semicol
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
				return STM_SEP;
			case ';':
				return STM_SEP;
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
