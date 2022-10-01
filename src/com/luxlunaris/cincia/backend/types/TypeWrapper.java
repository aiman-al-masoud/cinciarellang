package com.luxlunaris.cincia.backend.types;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
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
public class TypeWrapper extends AbstractCinciaObject implements Type {

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
//		System.out.println(other.getClass());
//		System.out.println(this+" "+other);
//		System.out.println("is it a match?");
		other = other.unwrap();
//		System.out.println(this.type+" "+other);
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
//		System.out.println("hello? equals or no?");
//		return this.type 
//		return new CinciaBool(false);
		return new CinciaBool(this.matches((Type) other)); //TODO
	}

}
