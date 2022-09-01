package com.luxlunaris.cincia.backend;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

public class CinciaBool extends AbstractCinciaObject {

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
	public AbstractCinciaObject __add__(AbstractCinciaObject other) {
		throw new RuntimeException("Addition operators undefined on type bool");
	}

	@Override
	public AbstractCinciaObject __sub__(AbstractCinciaObject other) {
		throw new RuntimeException("Addition operators undefined on type bool");
	}

	@Override
	public AbstractCinciaObject __mul__(AbstractCinciaObject other) {
		throw new RuntimeException("Multiplication operators undefined on type bool");
	}

	@Override
	public AbstractCinciaObject __mod__(AbstractCinciaObject other) {
		throw new RuntimeException("Multiplication operators undefined on type bool");
	}

	@Override
	public AbstractCinciaObject __div__(AbstractCinciaObject other) {
		throw new RuntimeException("Multiplication operators undefined on type bool");
	}

	@Override
	public AbstractCinciaObject __neg__() {
		throw new RuntimeException("");
	}

	@Override
	public AbstractCinciaObject __or__(AbstractCinciaObject other) {

		try {
			CinciaBool o = (CinciaBool)other;
			return new CinciaBool(value || o.value);
		}catch (ClassCastException e) {

		}

		throw new RuntimeException("");
	}

	@Override
	public AbstractCinciaObject __and__(AbstractCinciaObject other) {

		try {
			CinciaBool o = (CinciaBool)other;
			return new CinciaBool(value && o.value);
		}catch (ClassCastException e) {

		}

		throw new RuntimeException("");
	}

	@Override
	public AbstractCinciaObject __lt__(AbstractCinciaObject other) {
		throw new RuntimeException("");
	}

	@Override
	public AbstractCinciaObject __gt__(AbstractCinciaObject other) {
		throw new RuntimeException("");
	}

	@Override
	public AbstractCinciaObject __lte__(AbstractCinciaObject other) {
		throw new RuntimeException("");
	}

	@Override
	public AbstractCinciaObject __gte__(AbstractCinciaObject other) {
		throw new RuntimeException("");
	}

	@Override
	public AbstractCinciaObject __eq__(AbstractCinciaObject other) {
		
		try {
			
			CinciaBool otherBool = (CinciaBool)other;
			return new CinciaBool(value==otherBool.value);
			
		} catch (ClassCastException e) {
			
		}
		
		throw new RuntimeException("");
	}

	@Override
	public AbstractCinciaObject __ne__(AbstractCinciaObject other) {
		CinciaBool eq = (CinciaBool)this.__eq__(other);
		return new CinciaBool(!eq.value);
	}

	@Override
	public AbstractCinciaObject __not__() {
		return new CinciaBool(!value);
	}

	@Override
	public AbstractCinciaObject __str__() {
		return new CinciaString(value+"");
	}

	@Override
	public AbstractCinciaObject __init__(List<AbstractCinciaObject> args) {
		throw new RuntimeException("");
	}

	@Override
	public AbstractCinciaObject as(List<AbstractCinciaObject> args) {//TODO:!!!
		return null;
	}



}
