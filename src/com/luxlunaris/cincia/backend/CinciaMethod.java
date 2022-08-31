package com.luxlunaris.cincia.backend;

import java.util.List;

import com.luxlunaris.cincia.backend.Interpreter.Eval;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CinciaMethod extends CinciaFunction{
	
	
	private CinciaObject parent;
	
	public CinciaMethod(LambdaExpression lambdex) {
		super(lambdex);
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
