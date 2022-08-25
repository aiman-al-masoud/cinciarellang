package com.luxlunaris.cincia.frontend.ast.statements.jump;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;

public class ReturnStatement implements Statement{
	
//	public List<Expression> values;
	public Expression expression;
	
	public ReturnStatement() {
//		values = new ArrayList<Expression>();
	}
	
	@Override
	public Statement simplify() {
		this.expression = expression.simplify();
		return this;
	}
	
	@Override
	public String toString() {
		return "return "+expression+";";
	}
	
//	public void addValue(Expression value) {
//		this.values.add(value);
//	}
}
