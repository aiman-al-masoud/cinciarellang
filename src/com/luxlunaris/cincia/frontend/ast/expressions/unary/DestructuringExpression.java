package com.luxlunaris.cincia.frontend.ast.expressions.unary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.UnaryExpression;

/**
 * 
 * *arg
 * 
 */
public class DestructuringExpression implements UnaryExpression{

	public Expression arg;
	
	public DestructuringExpression(UnaryExpression arg) {
		this.arg = arg;
	}
}
