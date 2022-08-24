package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class ComparisonExpression extends AndExpression{
	
	public Operators op;
	public Expression left;
	public AddExpression right;
	
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
		
		this.right = (AddExpression) right.simplify();
		return this;
	}
	
}
