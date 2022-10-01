package com.luxlunaris.cincia.backend.primitives;

import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class CinciaPrimitiveType extends AbstractCinciaObject implements Type {

	public final Keywords primitiveType;

	public CinciaPrimitiveType(Keywords primitiveType) {
		super(null);
		this.type = this;
		this.primitiveType = primitiveType;
	}

	@Override
	public Expression simplify() {
		throw new RuntimeException("Cannot simplify CinciaPrimitiveType!");
	}

	@Override
	public boolean matches(Type other) {

		try {
			CinciaPrimitiveType otherPrimitiveType =  (CinciaPrimitiveType)other;
			return this.primitiveType == otherPrimitiveType.primitiveType;

		} catch (ClassCastException e) {

		}

		return false;
	}
	
	@Override
	public String toString() {
		return primitiveType+"";
	}

}
