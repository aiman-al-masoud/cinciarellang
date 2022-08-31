package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CinciaFunction extends CinciaObject implements Callable{
	
	private CompoundStatement block;
	private Expression expression;
	private Signature signature;
	private CinciaObject parent;
	
	
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
	
	public void setParent(CinciaObject parent) {
		this.parent = parent;
	}
	
	public CinciaObject getParent() {
		return parent;
	}


	
	
	
	
}
