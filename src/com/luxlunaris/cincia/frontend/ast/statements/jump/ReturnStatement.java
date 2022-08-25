package com.luxlunaris.cincia.frontend.ast.statements.jump;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;

public class ReturnStatement implements Statement{
	
	public Expression expression;
	
	@Override
	public Statement simplify() {
		this.expression = expression.simplify();
		return this;
	}
	
	@Override
	public String toString() {
		return "return "+expression+";";
	}
	
}
