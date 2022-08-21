package com.luxlunaris.cincia.frontend.ast.statements.exception;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CatchClause {
	
	public Expression throwable;
	public CompoundStatement block;
	
}
