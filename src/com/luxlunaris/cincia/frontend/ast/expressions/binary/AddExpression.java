package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class AddExpression extends ComparisonExpression{

	public Operators op;
	public Expression left;
	public Expression right;

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

		this.right =  right.simplify();
		return this;
	}

	public static AddExpression make(Operators op, Expression left, Expression right) {
		AddExpression exp = new AddExpression();
		exp.op = op;
		exp.left = left;
		exp.right= right;
		return exp;
	}

}
