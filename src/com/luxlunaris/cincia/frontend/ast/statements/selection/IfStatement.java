package com.luxlunaris.cincia.frontend.ast.statements.selection;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class IfStatement implements Statement{
	
	public Expression cond;
	public CompoundStatement thenBlock;
	public CompoundStatement elseBlock;
	
	@Override
	public Statement simplify() {
		this.cond  = cond.simplify();
		this.thenBlock = (CompoundStatement) thenBlock.simplify();
		
		if(elseBlock!=null) {
			this.elseBlock  = (CompoundStatement) elseBlock.simplify();
		}
		
		return this;
	}
	
	
	
}
