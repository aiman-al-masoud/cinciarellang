package com.luxlunaris.cincia.frontend.ast.statements.labelled;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CaseStatement implements Statement{

	public Expression cond;
	public CompoundStatement block;
	public Expression expression;

	@Override
	public Statement simplify() {
		this.cond = cond.simplify();
		this.block = block !=null?(CompoundStatement) block.simplify():block;
		this.expression = expression!=null? expression.simplify() :expression;
		return this;
	}

	@Override
	public String toString() {
		return "case "+cond+" "+block;
	}

}
