package com.luxlunaris.cincia.frontend.ast.expressions.forexp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class ForExpression implements Expression{

	public List<Generator> generators;
	public CompoundStatement block;
	public Expression yield; // similar to Scala's yield

	public ForExpression() {
		generators = new ArrayList<>();
	}

	@Override
	public Expression simplify() {
		this.generators = generators.stream().map(g->  g.simplify() ).collect(Collectors.toList());

		if(block!=null) {
			block = (CompoundStatement) block.simplify();
		}

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
