package com.luxlunaris.cincia.frontend.ast.expressions.unary;

import com.luxlunaris.cincia.frontend.ast.expressions.SingleExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.UnaryExpression;

/**
 * 
 * *arg
 * 
 */
public class DestructuringExpression extends SingleExpression implements UnaryExpression{

	public Expression arg;
	
	public DestructuringExpression(UnaryExpression arg) {
		this.arg = arg;
	}
	
	@Override
	public Expression simplify() {
		return new DestructuringExpression((UnaryExpression)arg.simplify());
	}
}
