package com.luxlunaris.cincia.frontend.nodes.statements.iteration;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.nodes.expressions.Expression;
import com.luxlunaris.cincia.frontend.nodes.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.nodes.statements.Statement;
import com.luxlunaris.cincia.frontend.tokens.Identifier;

public class ForStatement implements Statement{
	
	
	public List<Identifier> loopVarsList;
	public Expression iterable;
	public CompoundStatement block;
	
	public ForStatement() {
		loopVarsList  = new ArrayList<Identifier>();
	}
	
	
	
	
	
	
	
	
}
