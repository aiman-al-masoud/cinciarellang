package com.luxlunaris.cincia.frontend.ast.tokens.operator;

import java.util.Arrays;

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
//	QUESTION_MARK,
	COMPARE,
	LT,
	GT,
	LTE,
	GTE,
	NE,
	AND,
	OR,
	NOT,
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
			case "!=":
				return NE;
//			case "?":
//				return QUESTION_MARK;
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
	
	public static boolean isOperator(char c) {
		return "+-*/%=!?<>&|".contains(c+"");
	}
	
	public static boolean isAddOperator(Object op) {
		return op==PLUS || op==MINUS;
	}
	
	public static boolean isMulOperator(Object op) {
		return op==ASTERISK || op==DIV || op==MOD; // all left assoc
	}
	
	public static boolean isComparisonOperator(Object op) {
		return Arrays.asList(COMPARE, NE, LT, GT, LTE, GTE).contains(op);
	}
	
	
	public static boolean isReassignmentOperator(Object op) {
		return Arrays.asList(PLUSPLUS, MINUSMINUS, PLUS_ASSIGN, MINUS_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN).contains(op);
	}
	
	
	

}
