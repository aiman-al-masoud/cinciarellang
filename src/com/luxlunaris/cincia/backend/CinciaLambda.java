package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CinciaLambda {
	
	private CompoundStatement block;
	private Expression expression;
	private Signature signature;
	
	
	public CinciaLambda(Signature signature, CompoundStatement block) {
		this.block = block;
	}
	
	public CinciaLambda(Signature signature, Expression expression) {
		this.expression = expression;
	}
	
	
	
	
	
	
	
	
	
	
}
