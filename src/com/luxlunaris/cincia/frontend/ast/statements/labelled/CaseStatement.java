package com.luxlunaris.cincia.frontend.ast.statements.labelled;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CaseStatement implements Statement{
	
	
	public Expression cond;
	public CompoundStatement block;
	
	@Override
	public Statement simplify() {
		this.cond = cond.simplify();
		this.block = (CompoundStatement) block.simplify();
		return this;
	}
	
}
