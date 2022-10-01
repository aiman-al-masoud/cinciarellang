package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;


public class IdentifierType extends OneNameType{

	public String value;

	public IdentifierType(String id) {
		this.value = id;
	}

	@Override
	public String toString() {
		return value+"";
	}

	@Override
	public boolean matches(Type other) {

		try {

			return value.equals(((IdentifierType)other).value);
		} catch (ClassCastException e) {

		}

		return false;
	}

	@Override
	public Type unwrap() {
		return this;
	}

	@Override
	public Type resolve(Eval eval, Enviro enviro) {		
		return (Type) enviro.get(value);
	}
}
