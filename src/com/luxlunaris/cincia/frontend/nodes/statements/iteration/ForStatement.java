package com.luxlunaris.cincia.frontend.nodes.statements.iteration;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;
import com.luxlunaris.cincia.frontend.nodes.interfaces.Statement;
import com.luxlunaris.cincia.frontend.nodes.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.nodes.tokens.Identifier;

public class ForStatement implements Statement{
	
	
	public List<Identifier> loopVarsList;
	public Expression iterable;
	public CompoundStatement block;
	
	public ForStatement() {
		loopVarsList  = new ArrayList<Identifier>();
	}
	
	
	
	
	
	
	
	
}
