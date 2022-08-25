package com.luxlunaris.cincia.frontend.ast.tokens.punctuation;


public enum Punctuations {
	

	STM_SEP, //statement separator
	COL, 
	PAREN_OPN, 
	PAREN_CLS, 
	SQBR_OPN, 
	SQBR_CLS, 
	CURLY_OPN, 
	CURLY_CLS,
	COMMA, 
	DOT, 
	SLASH_BCK,
	QUESTION_MARK;
	
	public static Punctuations fromChar(char value){
		
		switch (value) {
		
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
			case '?':
				return QUESTION_MARK;
		}
		
		return null;
		
	}
	
	
	public static boolean isPunctuation(char value) {
		return fromChar(value) != null;
	}
	
	
	
	
	
}
