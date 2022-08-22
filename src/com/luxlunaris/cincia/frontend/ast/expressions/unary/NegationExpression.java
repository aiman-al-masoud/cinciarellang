package com.luxlunaris.cincia.frontend.ast.expressions.unary;

import com.luxlunaris.cincia.frontend.ast.interfaces.UnaryExpression;

/**
 * 
 * ! arg
 *
 */
public class NegationExpression implements UnaryExpression{
	
	public UnaryExpression arg;
	
	
	public NegationExpression(UnaryExpression arg) {
		this.arg = arg;
	}
}


