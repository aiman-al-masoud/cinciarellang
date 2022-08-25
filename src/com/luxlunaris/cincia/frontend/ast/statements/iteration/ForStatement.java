package com.luxlunaris.cincia.frontend.ast.statements.iteration;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class ForStatement implements Statement{
	
	
	public List<Identifier> loopVars;
	public Expression iterable;
	public CompoundStatement block;
	
	public ForStatement() {
		loopVars  = new ArrayList<Identifier>();
	}

	@Override
	public Statement simplify() {
		this.iterable = iterable.simplify();
		this.block = (CompoundStatement) block.simplify();
		return this;
	}
	
	
	
	
	
	
	
	
}
