package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CinciaMethod extends CinciaFunction{
	
	
	CinciaObject parent;
	
	public CinciaMethod(Signature signature, CompoundStatement block) {
		super(signature, block);
	}

	public void setParent(CinciaObject parent) {
		this.parent = parent;
	}
	
	
	
	

}
