package com.luxlunaris.cincia.backend.callables;

import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.object.Magic;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
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
		// make sure params are passed by copy, never by reference
		params = params.stream().map(p->p.byCopy()).collect(Collectors.toList());
		setImmutable(); // a pure function will never change
	}

	public CinciaObject run(List<CinciaObject> args) {
		Enviro enviro = new Enviro(null); // brand new empty env
		enviro.set(Magic.THIS.toString(), this);//reference to self, required to write recursive pure functions
		// Make sure args can't ever be passed by reference:
//		List<CinciaObject> argsCopy = args.stream().map(o->o.copy(null)).collect(Collectors.toList());
		return super.run(args, enviro);
	}

	@Override
	public CinciaObject run(List<CinciaObject> args, Enviro enviro) {
		throw new RuntimeException("Can't bind pure function to externally supplied environment!");
	}

	@Override
	public CinciaString __str__() {
		return new CinciaString(toString());
	}

	@Override
	public String toString() {
		return lambdex.signature.toString();
	}

	@Override
	public CinciaObject copy(List<CinciaObject> args) {
		return this; // since pure functions are immutable...
	}

}
