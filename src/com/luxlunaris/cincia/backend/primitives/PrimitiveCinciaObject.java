package com.luxlunaris.cincia.backend.primitives;

import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.CinciaCinciaClass;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

// TODO: pull up here some code from the subclasses
public class PrimitiveCinciaObject extends CinciaCinciaClass {
	
	protected boolean isInstance;
	
	@Override
	public boolean matches(Type other) {
		return getClass() == other.getClass();
	}
	
	@Override
	public CinciaObject copy(List<CinciaObject> args) {
		return this;
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
	public CinciaObject __add__(CinciaObject other) {
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.PLUS));
	}
	
	@Override
	public CinciaObject __mul__(CinciaObject other) {
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.ASTERISK));
	}



	@Override
	public CinciaBool __eq__(CinciaObject other) {
		return (CinciaBool)CinciaObject.wrap(toJava().equals(other.toJava()));
	}

	@Override
	public CinciaString __str__() {
		return (CinciaString) CinciaObject.wrap(toJava()+"");
	}

	@Override
	public CinciaBool __bool__() {
		return (CinciaBool) CinciaObject.wrap(Alu.perform( this.toJava() , 0, Operators.NE));
	}


}
