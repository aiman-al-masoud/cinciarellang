package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;


//TODO: implement comparison operators!!!!
//TODO: better error messages
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
		
		throw new RuntimeException("Unsupported subtraction!");
	}
	
	
	@Override
	public CinciaObject __mul__(CinciaObject other) {
		
		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaInt(value*otherInt.value);
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value*otherFloat.getValue());
		}catch (ClassCastException e) {

		}
		
		throw new RuntimeException("Unsupported multiplication!");
	}
	
	@Override
	public CinciaObject __div__(CinciaObject other) {
		
		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaInt(value/otherInt.value);
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value/otherFloat.getValue());
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
	
	@Override
	public CinciaObject __lt__(CinciaObject other) {
		
		try {
			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaBool(value < otherInt.getValue());
		}catch (ClassCastException e) {
		
		}
		
		try {
			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaBool(value < otherFloat.getValue());
		}catch (ClassCastException e) {
		
		}
		
		throw new RuntimeException();
	}
	
	


}
