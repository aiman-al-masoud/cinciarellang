package com.luxlunaris.cincia.frontend.ast.expressions;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

public class TernaryExpression implements Expression{
	
	public Expression cond;
	public Expression thenExpression;
	public Expression elseExpression;
	
	@Override
	public Expression simplify() {
		
		TernaryExpression tE = new TernaryExpression();
		tE.cond = cond.simplify();
		tE.thenExpression = thenExpression.simplify();
		tE.elseExpression = elseExpression.simplify();
		return tE;
	}
}
