package com.luxlunaris.cincia.frontend.ast.expressions;

import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class IfExpression implements Expression{
	
	public Expression cond;
	public CompoundStatement thenBlock;
	public CompoundStatement elseBlock;
	
	public Expression thenExpression;
	public Expression elseExpression;
	
	@Override
	public Expression simplify() {
		this.cond  = cond.simplify();
		
		if(thenBlock!=null) {
			this.thenBlock = (CompoundStatement) thenBlock.simplify();
		}
		
		if(elseBlock!=null) {
			this.elseBlock  = (CompoundStatement) elseBlock.simplify();
		}
		
		if(thenExpression!=null) {
			this.thenExpression = thenExpression.simplify();
		}
		
		if(elseExpression!=null) {
			this.elseExpression = elseExpression.simplify();
		}
		
		return this;
	}
	
	@Override
	public String toString() {
		return "if "+cond+" then "+thenBlock+" else "+elseBlock;
	}
	
	public Ast getThen() { //TODO: throw if both null?
		return thenExpression!=null? thenExpression : thenBlock;
	}
	
	public Ast getElse() { //TODO: throw if both null?
		return elseExpression!=null? elseExpression : elseBlock;
	}
	
	
}
