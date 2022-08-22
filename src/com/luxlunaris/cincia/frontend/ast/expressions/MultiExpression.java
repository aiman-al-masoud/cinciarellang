package com.luxlunaris.cincia.frontend.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

/**
 * Sequence of comma-separated single expressions		
 * 
 *
 */
public class MultiExpression implements Expression{
	
	public List<Expression> expressions;
	
	public MultiExpression() {
		expressions = new ArrayList<Expression>();
	}
	
	public void addExpression(Expression expression) {
		expressions.add(expression);
	}
	
}
