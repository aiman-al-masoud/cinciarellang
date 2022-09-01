package com.luxlunaris.cincia.backend;

import java.util.List;

import com.luxlunaris.cincia.backend.Interpreter.Eval;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CinciaMethod extends CinciaFunction{
	
	
	private AbstractCinciaObject parent;
	
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
	public AbstractCinciaObject run(List<AbstractCinciaObject> args) {
		//TODO PROBLEM: this overwrites also stuff in this given how this was implemented
		return super.run(args, parent.getEnviro());
	}
	
	

}
