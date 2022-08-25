package com.luxlunaris.cincia.frontend.ast.statements;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;

public class ExpressionStatement implements Statement{
	
	public Expression expression;
	
	public ExpressionStatement(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		return expression.toString();
	}

	@Override
	public Statement simplify() {
		this.expression = expression.simplify();
		return this;
	}
	
}
