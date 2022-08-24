package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

public class AndExpression implements Expression{
	
	public Expression left;
	public ComparisonExpression right;
	
	@Override
	public String toString() {
		return "("+left+" && "+right+")";
	}
	
}
