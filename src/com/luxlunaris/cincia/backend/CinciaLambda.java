package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CinciaLambda extends CinciaObject{
	
	private CompoundStatement block;
	private Expression expression;
	private Signature signature;
	
	
	public CinciaLambda(Signature signature, CompoundStatement block) {
		super(null);
		this.block = block;
	}
	
	public CinciaLambda(Signature signature, Expression expression) {
		super(null);
		this.expression = expression;
	}
	
	

	
	
	
	
}
