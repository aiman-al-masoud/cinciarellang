package com.luxlunaris.cincia.frontend.nodes.statements;

import java.util.ArrayList;
import java.util.List;


public class CompoundStatement implements Statement{
	
	
	public List<Statement> statementsList;
	
	public CompoundStatement() {
		statementsList = new ArrayList<Statement>();
	}
	
	public void add(Statement statement) {
		statementsList.add(statement);
	}
	
	
}
