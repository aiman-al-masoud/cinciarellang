package com.luxlunaris.cincia.frontend.ast.tokens.keyword;



public enum Keywords {
	
	 TRUE,
	 FALSE,
	 THROW,
	 TRY,
	 CATCH,
	 FINALLY,
	 CASE,
	 DEFAULT,
	 IMPORT,
	 FROM,
	 AS,
	 RETURN,
	 CONTINUE,
	 BREAK,
	 IF,
	 ELSE,
	 MATCH,
	 FOR,
	 OF,
	 WHILE,
	 CLASS,
	 INTERFACE,
	 LISTENSTO,
	 IMPLEMENTS,
	 EXTENDS, 
	 WHERE,
	 PRIVATE,
	 STATIC,
	 FINAL,
	 GET,
	 SET,
	 SINGLETON,
	 POOLED,
	 IMMUTABLE,
	 IS,
	 IN,
	 INT,
	 FLOAT,
	 BOOL;	
	
	public static Keywords fromString(String value){
		
		try{
			return Keywords.valueOf( value.toUpperCase() );
		}catch (Exception e) {
			return null;
		}
	}
	
}
