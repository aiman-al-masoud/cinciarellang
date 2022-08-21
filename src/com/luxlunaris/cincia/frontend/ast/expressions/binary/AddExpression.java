package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class AddExpression implements Expression{
	
	public Operators op;
	public Expression left;
	public MulExpression right;
	
	
}
