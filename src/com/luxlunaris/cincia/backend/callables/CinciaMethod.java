package com.luxlunaris.cincia.backend.callables;

import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.interfaces.WrappedFunction;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;


public class CinciaMethod extends CinciaFunction{

	public CinciaObject parent;

	public CinciaMethod(LambdaExpression lambdex, Eval eval) {
		super(lambdex, eval);
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
			copy = new CinciaMethod(wrappedFunction, null);
		}else {
			copy = new CinciaMethod(lambdex, eval);
		}

		return copy;
	}

	/**
	 * Can have side effects on the parent's env when using this
	 * to access its vars.
	 * @param args
	 * @param eval
	 * @return
	 */
	public CinciaObject run(List<CinciaObject> args) {
		//TODO  PROBLEM 1: this overwrites also stuff in this given how this was implemented
		//TODO: PROBLEM 2: recursive methods are broken, because all calls on the stack refer to the same environment!!
		//TODO: Fixed PROBLEM 3 by shallow-cloning env, but now you can't set instance vars without using this.
		return super.run(args, parent.getEnviro().newChild());
	}



}
