package com.luxlunaris.cincia.frontend.ast.statements.exception;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class TryStatement implements Statement{
	
	
	public CompoundStatement tryBlock;
	public List<CatchClause> catchClausesList;
	public CompoundStatement finallyBlock;
	
	
	public TryStatement() {
		catchClausesList = new ArrayList<CatchClause>();
	}
	
	
	public void add(CatchClause catchClause) {
		this.catchClausesList.add(catchClause);
	}
	
	
}
