package com.luxlunaris.cincia.frontend.ast.expressions;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class WhileExpression extends SingleExpression {
	
	public Expression cond;
	public CompoundStatement block;
	public Expression yield; 

	
	@Override
	public Expression simplify() {
		this.cond = cond.simplify();
		
		if(block!=null) {
			block = (CompoundStatement) block.simplify();
		}

		if(yield!=null) {
			yield = yield.simplify();
		}
		
		return this;
	}
	
	@Override
	public String toString() {
		return "while "+cond+" then "+block;
	}
	
}
