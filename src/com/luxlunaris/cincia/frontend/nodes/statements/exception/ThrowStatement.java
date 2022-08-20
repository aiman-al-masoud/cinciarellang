package com.luxlunaris.cincia.frontend.nodes.statements.exception;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;
import com.luxlunaris.cincia.frontend.nodes.interfaces.Statement;

public class ThrowStatement implements Statement{
	
	
	public Expression throwable;
	
	
	public ThrowStatement(Expression throwable){
		this.throwable  =throwable;
	}
	
}
