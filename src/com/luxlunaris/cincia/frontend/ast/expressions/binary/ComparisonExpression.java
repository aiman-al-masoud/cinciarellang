package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class ComparisonExpression implements Expression{
	
	public Operators op;
	public Expression left;
	public AddExpression right;
	
}
