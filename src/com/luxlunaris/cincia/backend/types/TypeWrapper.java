package com.luxlunaris.cincia.backend.types;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.object.BaseCinciaObject;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

/**
 * 
 * Wraps a type from com.luxlunaris.cincia.frontend.ast.expressions.type
 * into a CiciaObject envelope.
 *
 */
public class TypeWrapper extends BaseCinciaObject implements Type {

	public TypeWrapper(Type type) {
		super(type);
		//		System.out.println(type);
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

	@Override
	public Type unwrap() {
		return type;
	}

	@Override
	public Type resolve(Eval eval, Enviro enviro) {
		return this;
	}


	@Override
	public CinciaBool __eq__(CinciaObject other) {
		return new CinciaBool(this.matches((Type) other)); //TODO
	}

}
