package com.luxlunaris.cincia.backend.primitives;

import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class Alu {

	public static Object perform(Object a, Object b, Operators op) {


		if(a instanceof Integer && b instanceof Integer && Operators.isArithmetic(op)) {
			return performArithmeticInt((int)a, (int)b, op);

		}else if (a instanceof Number && b instanceof Number && Operators.isArithmetic(op)) {
			return performArithmetic((double)a, (double)b, op);

		}else if (a instanceof Number && b instanceof Number && Operators.isComparisonOperator(op)) {
			return performComparison((double)a, (double)b, op);

		}else if (a instanceof Boolean && b instanceof Boolean) {
			return performLogical((boolean)a, (boolean)b, op);

		}


		throw new RuntimeException("Unknown types for operation "+op);
	}


	protected static int performArithmeticInt(int a, int b, Operators op) {

		switch (op) {
		case PLUS:
			return a + b;
		case MINUS:
			return a - b;
		case ASTERISK:
			return a * b;
		case DIV:
			return a / b;
		case MOD:
			return a % b;
		default:
			throw new RuntimeException("Unrecognized operation!");
		}
	}

	protected static double performArithmetic(double a, double b, Operators op) {

		switch (op) {
		case PLUS:
			return a + b;
		case MINUS:
			return a - b;
		case ASTERISK:
			return a * b;
		case DIV:
			return a / b;
		case MOD:
			return a % b;
		default:
			throw new RuntimeException("Unrecognized operation!");
		}

	}

	protected static boolean performComparison(double a, double b, Operators op) {


		switch (op) {
		case COMPARE:
			return a == b;
		case NE:
			return a != b;
		case LT:
			return a < b;
		case GT:
			return a > b;
		case LTE:
			return a <= b;
		case GTE: 
			return a >= b;
		default:
			throw new RuntimeException("Unrecognized operation!");
		}

	}

	protected static boolean performLogical(boolean a, boolean b, Operators op) {

		switch (op) {
		case COMPARE:
			return a == b;
		case NE:
			return a != b;
		case AND:
			return a && b;
		case OR:
			return a || b;

		default:
			throw new RuntimeException("Unrecognized operation!");
		}

	}



}
