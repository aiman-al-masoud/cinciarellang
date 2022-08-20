package com.luxlunaris.cincia.frontend.nodes.statements.exception;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;
import com.luxlunaris.cincia.frontend.nodes.statements.CompoundStatement;

public class CatchClause {
	
	public Expression throwable;
	public CompoundStatement block;
	
	
	public CatchClause(Expression throwable, CompoundStatement block) {
		this.throwable = throwable;
		this.block = block;
	}
	
}
