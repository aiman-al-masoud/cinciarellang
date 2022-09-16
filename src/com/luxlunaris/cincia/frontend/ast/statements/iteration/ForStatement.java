package com.luxlunaris.cincia.frontend.ast.statements.iteration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class ForStatement implements Statement{


	//	public List<Identifier> loopVars;
	//	public Expression iterable;
	public List<Generator> generators;
	public CompoundStatement block;

	public ForStatement() {
		//		loopVars  = new ArrayList<Identifier>();
		generators = new ArrayList<>();
	}

	@Override
	public Statement simplify() {
		//		this.iterable = iterable.simplify();

		this.generators = generators.stream().map(g->  g.simplify() ).collect(Collectors.toList());
		this.block = (CompoundStatement) block.simplify();
		return this;
	}

	@Override
	public String toString() {
		//		return "for "+loopVars+" in "+iterable+""+block;
		return "forloop: generators="+ generators+" block="+block;
	}


	







}
