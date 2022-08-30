package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.declarations.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CinciaFunction extends CinciaObject{
	
	private CompoundStatement block;
	private Expression expression;
	private Signature signature;
	
	
	public CinciaFunction(Signature signature, CompoundStatement block) {
		super(null);
		this.block = block;
		this.signature = signature;
	}
	
	public CinciaFunction(Signature signature, Expression expression) {
		super(null);
		this.expression = expression;
		this.signature = signature;
	}
	
	

	
	
	
	
}
