package com.luxlunaris.cincia.backend;

import java.util.List;

import com.luxlunaris.cincia.backend.Interpreter.Eval;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;

public class CinciaMethod extends CinciaFunction{
	
	
	public CinciaObject parent;
	
	public CinciaMethod(LambdaExpression lambdex, Eval eval) {
		super(lambdex, eval);
	}
	
	public CinciaMethod(WrappedFunction wrappedFunction) {
		super(wrappedFunction);
	}
	
	public void setParent(AbstractCinciaObject parent) {
		this.parent = parent;
	}
	
	public AbstractCinciaObject getParent(AbstractCinciaObject parent) {
		return parent;
	}
	
	/**
	 * Runs on the ORIGINAL parent object's environment,
	 * has SIDE EFFECTS!
	 * @param args
	 * @param eval
	 * @return
	 */
	public CinciaObject run(List<CinciaObject> args) {
		//TODO PROBLEM: this overwrites also stuff in this given how this was implemented
		return super.run(args, parent.getEnviro());
	}
	
	

}
