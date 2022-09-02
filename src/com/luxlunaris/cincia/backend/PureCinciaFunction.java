package com.luxlunaris.cincia.backend;

import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.Interpreter.Eval;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;

/**
 * 
 * Doesn't even read from the global env, its only input are its arguments.
 * And (like regular functions), it can't write to the gloal env.
 * 
 */
public class PureCinciaFunction extends CinciaFunction {

	public PureCinciaFunction(LambdaExpression lambdex, Eval eval) {
		super(lambdex, eval);
	}
	
	public CinciaObject run(List<CinciaObject> args) {
		Enviro enviro = new Enviro(null);
		// TODO: make sure it can't be passed args by reference.
		List<CinciaObject> argsCopy = args.stream().map(o->o.copy(null)).collect(Collectors.toList());
		return super.run(argsCopy, enviro);
	}

}
