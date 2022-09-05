package com.luxlunaris.cincia.frontend.ast.interfaces;

import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public interface BinaryExpression extends Expression{

	static BinaryExpression make(Operators op, Expression left, Expression right) {

		
		switch (op) {
		
		case PLUS:
			AddExpression exp = new AddExpression();
			exp.op = op;
			exp.left = left;
			exp.right =right;
			return exp;

		default:
			throw new RuntimeException("No such binary operator!");
		}

	}

}
