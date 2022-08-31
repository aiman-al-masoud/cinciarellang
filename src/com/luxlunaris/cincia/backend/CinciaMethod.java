package com.luxlunaris.cincia.backend;

import java.util.List;

import com.luxlunaris.cincia.backend.CinciaFunction.Eval;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CinciaMethod extends CinciaFunction{
	
	
	private CinciaObject parent;
	
	public CinciaMethod(Signature signature, CompoundStatement block) {
		super(signature, block);
	}
	
	public CinciaMethod(Signature signature, Expression expression) {
		super(signature, expression);
	}

	public void setParent(CinciaObject parent) {
		this.parent = parent;
	}
	
	public CinciaObject getParent(CinciaObject parent) {
		return parent;
	}
	
	/**
	 * Runs on the ORIGINAL parent object's environment,
	 * has SIDE EFFECTS!
	 * @param args
	 * @param eval
	 * @return
	 */
	public CinciaObject run(Expression args, Eval eval) {
		//TODO PROBLEM: this overwrites also stuff in this given how this was implemented
		return super.run(args, parent.getEnviro(), eval);
	}
	
	

}
