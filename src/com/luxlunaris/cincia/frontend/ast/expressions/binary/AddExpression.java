package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class AddExpression extends ComparisonExpression{
	
	public Operators op;
	public Expression left;
//	public MulExpression right;
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
		
//		this.right = (MulExpression) right.simplify();
		this.right =  right.simplify();
		return this;
	}
}
