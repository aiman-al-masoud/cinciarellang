package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

//TODO: implement magic methods
public class CinciaInt extends AbstractCinciaObject {

	private int value;

	public CinciaInt(int value) {
		super(new PrimitiveType(PrimitiveType.INT));
		this.value = value;		
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public CinciaObject __add__(CinciaObject other) {

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaInt(value+otherInt.value);
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value+otherFloat.getValue());
		}catch (ClassCastException e) {

		}
		
		try {

			CinciaString otherStr = (CinciaString)other;
			return new CinciaString(value+otherStr.getValue());
		}catch (ClassCastException e) {

		}
		
		throw new RuntimeException("Unsupported addition!");

	}

	
	@Override
	public CinciaObject __sub__(CinciaObject other) {
		
		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaInt(value-otherInt.value);
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value-otherFloat.getValue());
		}catch (ClassCastException e) {

		}
		
		throw new RuntimeException("Unsupported addition!");
	}
	
	


}
