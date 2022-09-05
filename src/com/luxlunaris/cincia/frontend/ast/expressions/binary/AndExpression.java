package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

public class AndExpression extends OrExpression{
	
//	public Expression left;
//	public ComparisonExpression right;
//	public Expression right;
	
	@Override
	public String toString() {
		return "("+left+" && "+right+")";
	}
	
	@Override
	public Expression simplify() {
		this.left = left.simplify();
		
		if(right==null) {
			return left;
		}
		
		
//		this.right = (ComparisonExpression) right.simplify();
		this.right = right.simplify();
		return this;
	}
	
}
