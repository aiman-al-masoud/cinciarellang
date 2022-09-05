package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.UnaryExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class MulExpression extends AddExpression{

	@Override
	public String toString() {
		return "("+left+" "+op+" "+right+")";
	}

	@Override
	public Expression simplify() {

		this.left = left.simplify();

		if(right==null) {
			return left;
		}

		this.right = (UnaryExpression) right.simplify();
		return this;
	}

}
