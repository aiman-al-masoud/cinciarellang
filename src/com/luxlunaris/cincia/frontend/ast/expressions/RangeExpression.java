package com.luxlunaris.cincia.frontend.ast.expressions;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

public class RangeExpression implements Expression {
	
	public Expression from;
	public Expression to;

	@Override
	public Expression simplify() {
		this.from = from.simplify();
		this.to = to.simplify();
		return this;
	}
	
	@Override
	public String toString() {
		return "("+from+" to "+to+")";
	}

}
