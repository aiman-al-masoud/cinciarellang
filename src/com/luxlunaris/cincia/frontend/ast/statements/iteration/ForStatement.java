package com.luxlunaris.cincia.frontend.ast.statements.iteration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class ForStatement implements Statement{

	public List<Generator> generators;
	public CompoundStatement block;
	public Expression yield; // similar to Scala's yield

	public ForStatement() {
		generators = new ArrayList<>();
	}

	@Override
	public Statement simplify() {
		this.generators = generators.stream().map(g->  g.simplify() ).collect(Collectors.toList());
		this.block = (CompoundStatement) block.simplify();
		
		if(yield!=null) {
			yield = yield.simplify();
		}
		
		return this;
	}

	@Override
	public String toString() {
		return "forloop: generators="+ generators+" block="+block+" yield="+yield;
	}

}
