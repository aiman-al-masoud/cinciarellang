package com.luxlunaris.cincia.backend.types;

import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

/**
 * 
 * Wraps a type from com.luxlunaris.cincia.frontend.ast.expressions.type
 * into a CiciaObject envelope.
 *
 */
public class TypeWrapper extends AbstractCinciaObject implements Type {

	public TypeWrapper(Type type) {
		super(type);
	}

	@Override
	public Expression simplify() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean matches(Type other) {
		
		other = other.unwrap();

		return type.matches(other);
	}

	@Override
	public String toString() {
		return type+"";
	}

//	@Override
//	public Type getType() {
//		return type;
//	}

	@Override
	public Type unwrap() {
		return type;
	}

	@Override
	public Type resolve(Eval eval, Enviro enviro) {
		return this;
	}

}
