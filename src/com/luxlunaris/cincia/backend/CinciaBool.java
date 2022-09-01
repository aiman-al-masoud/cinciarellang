package com.luxlunaris.cincia.backend;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

public class CinciaBool extends CinciaObject {

	private boolean value;

	public CinciaBool(boolean value) {
		super(new PrimitiveType(PrimitiveType.BOOL));
		this.value = value;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public boolean __bool__() {
		return value;
	}

	@Override
	public CinciaObject __add__(CinciaObject other) {
		throw new RuntimeException("Addition operators undefined on type bool");
	}

	@Override
	public CinciaObject __sub__(CinciaObject other) {
		throw new RuntimeException("Addition operators undefined on type bool");
	}

	@Override
	public CinciaObject __mul__(CinciaObject other) {
		throw new RuntimeException("Multiplication operators undefined on type bool");
	}

	@Override
	public CinciaObject __mod__(CinciaObject other) {
		throw new RuntimeException("Multiplication operators undefined on type bool");
	}

	@Override
	public CinciaObject __div__(CinciaObject other) {
		throw new RuntimeException("Multiplication operators undefined on type bool");
	}

	@Override
	public CinciaObject __neg__() {
		throw new RuntimeException("");
	}

	@Override
	public CinciaObject __or__(CinciaObject other) {

		try {
			CinciaBool o = (CinciaBool)other;
			return new CinciaBool(value || o.value);
		}catch (ClassCastException e) {

		}

		throw new RuntimeException("");
	}

	@Override
	public CinciaObject __and__(CinciaObject other) {

		try {
			CinciaBool o = (CinciaBool)other;
			return new CinciaBool(value && o.value);
		}catch (ClassCastException e) {

		}

		throw new RuntimeException("");
	}

	@Override
	public CinciaObject __lt__(CinciaObject other) {
		throw new RuntimeException("");
	}

	@Override
	public CinciaObject __gt__(CinciaObject other) {
		throw new RuntimeException("");
	}

	@Override
	public CinciaObject __lte__(CinciaObject other) {
		throw new RuntimeException("");
	}

	@Override
	public CinciaObject __gte__(CinciaObject other) {
		throw new RuntimeException("");
	}

	@Override
	public CinciaObject __eq__(CinciaObject other) {
		
		try {
			
			CinciaBool otherBool = (CinciaBool)other;
			return new CinciaBool(value==otherBool.value);
			
		} catch (ClassCastException e) {
			
		}
		
		throw new RuntimeException("");
	}

	@Override
	public CinciaObject __ne__(CinciaObject other) {
		CinciaBool eq = (CinciaBool)this.__eq__(other);
		return new CinciaBool(!eq.value);
	}

	@Override
	public CinciaObject __not__() {
		return new CinciaBool(!value);
	}

	@Override
	public CinciaObject __str__() {
		return new CinciaString(value+"");
	}

	@Override
	public CinciaObject __init__(List<CinciaObject> args) {
		throw new RuntimeException("");
	}

	@Override
	public CinciaObject as(List<CinciaObject> args) {//TODO:!!!
		return null;
	}



}
