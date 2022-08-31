package com.luxlunaris.cincia.backend;

import java.util.List;

import com.luxlunaris.cincia.backend.CinciaFunction.Eval;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CinciaMethod extends CinciaFunction{
	
	
	CinciaObject parent;
	
	public CinciaMethod(Signature signature, CompoundStatement block) {
		super(signature, block);
	}
	
	public CinciaMethod(Signature signature, Expression expression) {
		super(signature, expression);
	}

	public void setParent(CinciaObject parent) {
		this.parent = parent;
	}
	
	/**
	 * Runs on the ORIGINAL parent object's environment,
	 * has SIDE EFFECTS!
	 * @param args
	 * @param eval
	 * @return
	 */
	public CinciaObject run(List<Expression> args, Eval eval) {
		return super.run(args, parent.getEnviro(), eval);
	}
	
	

}
