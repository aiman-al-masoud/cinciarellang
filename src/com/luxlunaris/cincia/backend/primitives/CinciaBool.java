package com.luxlunaris.cincia.backend.primitives;

import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

public class CinciaBool extends PrimitiveCinciaObject {

	private boolean value;

	public CinciaBool(boolean value) {
		super(new PrimitiveType(PrimitiveType.BOOL));
		this.value = value;
	}

	@Override
	public CinciaBool __bool__() {
		return this;
	}

	@Override
	public CinciaBool __neg__() {
		return new CinciaBool(!value);
	}

	@Override
	public CinciaObject __or__(CinciaObject other) {
		return new CinciaBool(  value || other.__bool__().toJava()  );
	}

	@Override
	public CinciaObject __and__(CinciaObject other) {
		return new CinciaBool(value && other.__bool__().toJava());
	}
	
	@Override
	public CinciaBool __eq__(CinciaObject other) {
		
		try {
			
			CinciaBool otherBool = (CinciaBool)other;
			return new CinciaBool(value==otherBool.value);
			
		} catch (ClassCastException e) {
			
		}
		
		throw new RuntimeException("");
	}

	@Override
	public CinciaBool __ne__(CinciaObject other) {
		return new CinciaBool(!__eq__(other).toJava());
	}

	@Override
	public CinciaBool __not__() {
		return new CinciaBool(!value);
	}

	@Override
	public CinciaString __str__() {
		return new CinciaString(value+"");
	}
	
	@Override
	public CinciaObject as(List<CinciaObject> args) {//TODO:!!!
		return null;
	}

	@Override
	public Boolean toJava() {
		return value;
	}

}
