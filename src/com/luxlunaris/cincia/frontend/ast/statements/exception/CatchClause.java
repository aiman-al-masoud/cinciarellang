package com.luxlunaris.cincia.frontend.ast.statements.exception;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CatchClause {
	
//	public Expression throwable;
	public Declaration throwable;

	public CompoundStatement block;
	
	
	public CatchClause simplify() {
		this.throwable = throwable.simplify();
		this.block = (CompoundStatement) block.simplify();
		return this;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "catch "+throwable+" "+block;
	}
	
}
