package com.luxlunaris.cincia.frontend.ast.statements;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;


public class CompoundStatement implements Statement{
	
	
	public List<Statement> statements;
	
	public CompoundStatement() {
		statements = new ArrayList<Statement>();
	}
	
	public void add(Statement statement) {
		statements.add(statement);
	}
	
	
}
