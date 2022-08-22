package com.luxlunaris.cincia.frontend.ast.expressions.primary;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PrimaryExpression;

/**
 * This is to say that a PrimaryExpression could
 * contain any other kind of expression.
 *
 */
public class BracketedExpression implements PrimaryExpression {
	
	public Expression expression;
	
	public BracketedExpression(Expression expression) {
		this.expression = expression;
	}
	
}
