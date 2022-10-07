package com.luxlunaris.cincia.frontend.ast.expressions;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class IfExpression implements Expression{
	
	public Expression cond;
	public CompoundStatement thenBlock;
	public CompoundStatement elseBlock;
	
	@Override
	public Expression simplify() {
		this.cond  = cond.simplify();
		this.thenBlock = (CompoundStatement) thenBlock.simplify();
		
		if(elseBlock!=null) {
			this.elseBlock  = (CompoundStatement) elseBlock.simplify();
		}
		
		return this;
	}
	
	@Override
	public String toString() {
		return "if "+cond+" then "+thenBlock+" else "+elseBlock;
	}
	
	
}
