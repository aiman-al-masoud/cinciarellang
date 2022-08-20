package com.luxlunaris.cincia.frontend.nodes.statements.exception;

import com.luxlunaris.cincia.frontend.nodes.expressions.Expression;
import com.luxlunaris.cincia.frontend.nodes.statements.Statement;

public class ThrowStatement implements Statement{
	
	
	public Expression throwable;
	
	
	public ThrowStatement(Expression throwable){
		this.throwable  =throwable;
	}
	
}
