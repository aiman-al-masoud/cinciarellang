package com.luxlunaris.cincia.backend;

import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.Interpreter.Eval;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;

/**
 * 
 * They DON'T EVER READ from the global env, their only inputs are their arguments.
 * They DON'T EVER modify their arguments, even objects are passed by value (for real).
 * And (like regular/impure functions), they DON'T EVER write to the gloal env (of course).
 * 
 */
public class PureCinciaFunction extends CinciaFunction {

	public PureCinciaFunction(LambdaExpression lambdex, Eval eval) {
		super(lambdex, eval);
	}

	public CinciaObject run(List<CinciaObject> args) {
		Enviro enviro = new Enviro(null); // brand new empty env
		enviro.set("this", this);//reference to self, required to write recursive pure functions
		// Make sure args can't ever be passed by reference:
		List<CinciaObject> argsCopy = args.stream().map(o->o.copy(null)).collect(Collectors.toList());
		return super.run(argsCopy, enviro);
	}
	
	@Override
	public CinciaObject run(List<CinciaObject> args, Enviro enviro) {
		throw new RuntimeException("Can't bind pure function to externally supplied environment!");
	}

}
