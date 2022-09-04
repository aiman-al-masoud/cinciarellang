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
	
	
	public CinciaMethod(WrappedFunction wrappedFunction, CinciaObject parent) {
		super(wrappedFunction);
		this.parent = parent;
	}

	public void setParent(AbstractCinciaObject parent) {
		this.parent = parent;
	}


	@Override
	public CinciaMethod copy(List<CinciaObject> args) {

		CinciaMethod copy;

		if( wrappedFunction!=null ) {
			copy = new CinciaMethod(wrappedFunction);
		}else {
			copy = new CinciaMethod(lambdex, eval);
		}

		return copy;
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
		//TODO: PROBLEM: recursive methods are broken, because all calls on the stack refer to the same environment!!
		return super.run(args, parent.getEnviro().newChild());
	}



}
