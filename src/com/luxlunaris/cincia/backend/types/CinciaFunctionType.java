package com.luxlunaris.cincia.backend.types;

import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaFunctionType extends AbstractCinciaObject implements Type {

	public CinciaFunctionType(Type type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Expression simplify() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean matches(Type other) {
		// TODO Auto-generated method stub
		return false;
	}

}
