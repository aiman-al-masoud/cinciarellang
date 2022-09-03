package com.luxlunaris.cincia.frontend.ast.expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

public class PipeExpression implements Expression {
	
	
	List<Expression> expressions;
	
	public PipeExpression() {
		expressions = new ArrayList<Expression>();
	}
	
	public void add(Expression expression) {
		this.expressions.add(expression);
	}

	@Override
	public Expression simplify() {
		this.expressions = expressions.stream().map(e->e.simplify()).collect(Collectors.toList());
		return this;
	}
	
	
	@Override
	public String toString() {
		return expressions.toString();
	}

}
