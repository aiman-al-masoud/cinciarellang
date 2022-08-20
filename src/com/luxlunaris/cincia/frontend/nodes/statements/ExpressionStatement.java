package com.luxlunaris.cincia.frontend.nodes.statements;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;
import com.luxlunaris.cincia.frontend.nodes.interfaces.Statement;

public class ExpressionStatement implements Statement{
	
	public Expression expression;
	
	public ExpressionStatement(Expression expression) {
		this.expression = expression;
	}
	
}
