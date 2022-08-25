package com.luxlunaris.cincia.frontend.ast.statements.labelled;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class DefaultStatement implements Statement{
	
	public CompoundStatement block;

	@Override
	public Statement simplify() {
		this.block = (CompoundStatement) block.simplify();
		return this;
	}
	
}
