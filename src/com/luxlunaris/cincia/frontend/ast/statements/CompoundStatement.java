package com.luxlunaris.cincia.frontend.ast.statements;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;


public class CompoundStatement implements Statement{
	
	
	public List<Statement> statementsList;
	
	public CompoundStatement() {
		statementsList = new ArrayList<Statement>();
	}
	
	public void add(Statement statement) {
		statementsList.add(statement);
	}
	
	
}
