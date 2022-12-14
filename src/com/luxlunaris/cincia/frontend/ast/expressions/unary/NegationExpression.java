package com.luxlunaris.cincia.frontend.ast.expressions.unary;

import com.luxlunaris.cincia.frontend.ast.expressions.SingleExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.UnaryExpression;

/**
 * 
 * ! arg
 *
 */
public class NegationExpression extends SingleExpression implements UnaryExpression{
	
	public UnaryExpression arg;
	
	
	public NegationExpression(UnaryExpression arg) {
		this.arg = arg;
	}
	
	@Override
	public Expression simplify() {
		return new NegationExpression((UnaryExpression)arg.simplify());
	}
	
}


