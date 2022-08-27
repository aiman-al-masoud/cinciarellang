package com.luxlunaris.cincia.frontend.ast.statements.exception;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;

public class ThrowStatement implements Statement{
	
	
	public Expression throwable;
	
	
	public ThrowStatement(Expression throwable){
		this.throwable  =throwable;
	}
	
	@Override
	public Statement simplify() {
		this.throwable = throwable.simplify();
		return this;
	}
	
	@Override
	public String toString() {
		return "throw "+throwable;
	}
	
}
