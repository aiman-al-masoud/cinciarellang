package com.luxlunaris.cincia.backend.types;

import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

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
		return type.matches(other);
	}

}
