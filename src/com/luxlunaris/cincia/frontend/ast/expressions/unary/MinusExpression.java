package com.luxlunaris.cincia.frontend.ast.expressions.unary;

import com.luxlunaris.cincia.frontend.ast.expressions.SingleExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.UnaryExpression;

/**
 * 
 * -arg
 *
 */
public class MinusExpression extends SingleExpression implements UnaryExpression{

	public UnaryExpression arg;
	
	public MinusExpression(UnaryExpression arg) {
		this.arg = arg;
	}
	
	@Override
	public Expression simplify() {
		return new MinusExpression((UnaryExpression)arg.simplify());
	}
}
