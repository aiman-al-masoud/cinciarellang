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
	IS,
	IN,
	INT,
	FLOAT,
	BOOL,
	DEC,// decalaration	
	TO,
	STRING,
	PURE,
	REF,
	RDOUT;;

	public static Keywords fromString(String value){

		try{
			return Keywords.valueOf( value.toUpperCase() );
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}


}
