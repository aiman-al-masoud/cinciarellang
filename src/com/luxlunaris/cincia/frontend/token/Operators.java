package com.luxlunaris.cincia.frontend.token;

public enum Operators {
	
	PLUS,
	MINUS,
	ASTERISK, //mul or destructuring op
	DIV,
	MOD,
	POW,
	PLUSPLUS,
	MINUSMINUS,
	ASSIGN,
	COMPARE,
	QUESTION_MARK,
	LT,
	GT,
	LTE,
	GTE,
	AND,
	OR,
	NOT,
	COLUMN,
	ARROW,
	PLUS_ASSIGN,
	MINUS_ASSIGN,
	MUL_ASSIGN,
	DIV_ASSIGN,
	MOD_ASSIGN;
	
	
	public static Operators fromString(String string) {
		switch (string) {
			case "+":
				return PLUS;
			case "-":
				return MINUS;
			case "*":
				return ASTERISK;
			case "/":
				return DIV;
			case "%":
				return MOD;
			case "**":
				return POW;
			case "++":
				return PLUSPLUS;
			case "--":
				return MINUSMINUS;
			case "=":
				return ASSIGN;
			case "==":
				return COMPARE;
			case "?":
				return QUESTION_MARK;
			case "<":
				return LT;
			case ">":
				return GT;
			case "<=":
				return LTE;
			case ">=":
				return GTE;
			case "&&":
				return AND;
			case "||":
				return OR;
			case "!":
				return NOT;
			case ":":
				return COLUMN;
			case "->":
				return ARROW;
			case "+=":
				return PLUS_ASSIGN;
			case "-=":
				return MINUS_ASSIGN;
			case "*=":
				return MUL_ASSIGN;
			case "/=":
				return DIV_ASSIGN;
			case "%=":
				return MOD_ASSIGN;
			default:
				return null;
		}
	}
	
	
	public static boolean isOperator(String string) {
		return fromString(string) != null;
	}
	
	
	

}
