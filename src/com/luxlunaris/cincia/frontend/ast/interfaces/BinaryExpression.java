package com.luxlunaris.cincia.frontend.ast.interfaces;

import com.luxlunaris.cincia.frontend.ast.expressions.binary.AbstractBinaryExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.MulExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public interface BinaryExpression extends Expression{

	static BinaryExpression make(Operators op, Expression left, Expression right) {

		AbstractBinaryExpression binexp;

		switch (op) {

		case PLUS:
		case MINUS:
			binexp = new AddExpression();
			break;
		case ASTERISK:
		case DIV:
		case MOD:
			binexp = new MulExpression();
			break;
		case ASSIGN:
			binexp = new AssignmentExpression();
			break;


		default:
			throw new RuntimeException("No such binary operator!");
		}

		binexp.op = op;
		binexp.left = left;
		binexp.right =right;

		return binexp;
	}

}
