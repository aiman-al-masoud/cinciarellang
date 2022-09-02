package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

//TODO: implement magic methods
public class CinciaFloat extends AbstractCinciaObject {
	
	private double value;

	public CinciaFloat(double value) {
		super(new PrimitiveType(PrimitiveType.FLOAT));
		this.value = value;		
	}
	
	@Override
	public Double getValue() {
		return value;
	}
	

	@Override
	public CinciaObject __add__(CinciaObject other) {
		
		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value+otherFloat.getValue());
		}catch (ClassCastException e) {

		}
		

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaFloat(value+otherInt.getValue());
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

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value-otherFloat.getValue());
		}catch (ClassCastException e) {

		}
		
		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaFloat(value-otherInt.getValue());
		}catch (ClassCastException e) {

		}

		
		throw new RuntimeException("Unsupported subtraction!");
	}
	
	
	@Override
	public CinciaObject __mul__(CinciaObject other) {
		
		
		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value*otherFloat.getValue());
		}catch (ClassCastException e) {

		}
		
		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaFloat(value*otherInt.getValue());
		}catch (ClassCastException e) {

		}

		
		throw new RuntimeException("Unsupported multiplication!");
	}
	
	@Override
	public CinciaObject __div__(CinciaObject other) {
		
		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value/otherFloat.getValue());
		}catch (ClassCastException e) {

		}
		
		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaFloat(value/otherInt.getValue());
		}catch (ClassCastException e) {

		}
		
		throw new RuntimeException("Unsupported division!");
	}
	
	@Override
	public CinciaObject __mod__(CinciaObject other) {
		
		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaInt(value%otherInt.value);
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value%otherFloat.getValue());
		}catch (ClassCastException e) {

		}
		
		throw new RuntimeException("Unsupported modulo!");
	}
	
	


}
