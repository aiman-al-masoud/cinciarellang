package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

public class OrExpression extends AssignmentExpression{


	public Expression left;
//	public AndExpression right;
	public Expression right;
	
	@Override
	public String toString() {
		return "("+left+" || "+right+")";
	}
	
	@Override
	public Expression simplify() {
		
		this.left = left.simplify();
		
		if(right==null) {
			return left;
		}
		
//		this.right = (AndExpression) right.simplify();
		this.right = right.simplify();
		return this;
	}
	
	
// or other solution
//	public OrExpression left;
//	OrExpression e = new OrExpression();
//	OrExpression e2 = new OrExpression();
//	e2.left = null;
//	e2.right = new AndExpression();
//	e.left = e2;
//	e.right = new AndExpression();
	
}
