package com.luxlunaris.cincia.frontend.ast.expressions.binary;

import com.luxlunaris.cincia.frontend.ast.interfaces.BinaryExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public abstract class AbstractBinaryExpression implements BinaryExpression{
	
	public Expression left;
	public Expression right;
	public Operators op;
	
	

}
