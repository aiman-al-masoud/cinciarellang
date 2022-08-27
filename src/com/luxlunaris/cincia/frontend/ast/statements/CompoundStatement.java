package com.luxlunaris.cincia.frontend.ast.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;


public class CompoundStatement implements Statement{
	
	
	public List<Statement> statements;
	
	public CompoundStatement() {
		statements = new ArrayList<Statement>();
	}
	
	public void add(Statement statement) {
		statements.add(statement);
	}
	
	@Override
	public Statement simplify() {
		this.statements = statements.stream().map(s->s.simplify()).collect(Collectors.toList());
		return this;
	}
	
	@Override
	public String toString() {
		Optional<String> b = statements.stream().map(s->s+"").reduce((s1,s2)->s1+"; "+s2);
		return "{"+(b.isPresent()? b.get() : "")+"}";
	}
	
}
