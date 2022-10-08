package com.luxlunaris.cincia.frontend.ast.tokens.keyword;



public enum Keywords {

	TRUE,
	FALSE,
	THROW,
	TRY,
	CATCH,
	FINALLY,
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
	PRIVATE,
	STATIC,
	FINAL,
	IS,
	IN,
	INT,
	FLOAT,
	BOOL,
	TO,
	BY,
	STRING,
	REF,
	NONLOCAL,
	ANY,
	MODULE,
	THEN;

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
