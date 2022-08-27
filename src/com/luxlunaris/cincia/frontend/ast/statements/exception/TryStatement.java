package com.luxlunaris.cincia.frontend.ast.statements.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	@Override
	public Statement simplify() {
		this.tryBlock = (CompoundStatement) tryBlock.simplify();
		this.catchClausesList = catchClausesList.stream().map(c->c.simplify()).collect(Collectors.toList());
		
		if(finallyBlock!=null) {
			this.finallyBlock = (CompoundStatement) finallyBlock.simplify();
		}
		
		return this;
	}
	
	@Override
	public String toString() {
		System.out.println(catchClausesList);
		Optional<String> cc = catchClausesList.stream().map(c->c.toString()).reduce((c1, c2)->c1+" "+c2);
		return "try "+tryBlock+" "+(cc.isPresent()? cc.get() : "")+" finally "+finallyBlock;
	}
	
}
