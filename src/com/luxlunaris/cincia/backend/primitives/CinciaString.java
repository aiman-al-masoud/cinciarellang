package com.luxlunaris.cincia.backend.primitives;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

public class CinciaString extends PrimitiveCinciaObject {

	private String value;

	public CinciaString(String value) {
		super(new PrimitiveType(PrimitiveType.STRING));
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public CinciaObject __add__(CinciaObject other) {

		try {
			CinciaString otherStr =  (CinciaString)other;
			return new CinciaString(value+otherStr.value);
		} catch (ClassCastException e) {

		}

		throw new RuntimeException("Operator + undefined ...");
	}


	@Override
	public CinciaObject __eq__(CinciaObject other) {

		try {
			CinciaString otherStr = (CinciaString)other;
			return new CinciaBool(otherStr.value.equals(value));
		} catch (Exception e) {

		}

		return new CinciaBool(false);
	}


	@Override
	public String toString() {
		return "\""+value+"\"";
	}

	@Override
	public String toJava() {
		return value;
	}
	
	@Override
	public CinciaString get(int key) {
		return new CinciaString(value.charAt(key)+"");
	}


}
