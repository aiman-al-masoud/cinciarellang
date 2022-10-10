package com.luxlunaris.cincia.backend.primitives;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class CinciaNumber extends PrimitiveCinciaObject {

	public CinciaNumber(Type type) {
		super(type);
	}

	@Override
	public CinciaObject __lt__(CinciaObject other) {
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.LT));
	}

	@Override
	public CinciaObject __gt__(CinciaObject other) {
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.GT));
	}

	@Override
	public CinciaObject __lte__(CinciaObject other) {
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.LTE));
	}

	@Override
	public CinciaObject __gte__(CinciaObject other) {
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.GTE));
	}
	
	@Override
	public CinciaObject __div__(CinciaObject other) {
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.DIV));
	}
	
	@Override
	public CinciaObject __sub__(CinciaObject other) {
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.MINUS));
	}
	
	@Override
	public CinciaBool __eq__(CinciaObject other) {
		return (CinciaBool)CinciaObject.wrap(toJava().equals(other.toJava()));
	}
	
	@Override
	public CinciaString __str__() {
		return (CinciaString) CinciaObject.wrap(toJava()+"");
	}

}
