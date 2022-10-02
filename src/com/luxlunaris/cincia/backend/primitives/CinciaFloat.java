package com.luxlunaris.cincia.backend.primitives;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

//TODO: better error messages
public class CinciaFloat extends PrimitiveCinciaObject {

	private double value;

	public CinciaFloat(double value) {
		super(new TypeWrapper(new PrimitiveType(PrimitiveType.FLOAT)));
		this.value = value;		
	}

	@Override
	public CinciaObject __add__(CinciaObject other) {

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return CinciaObject.wrap(value+otherFloat.toJava());
		}catch (ClassCastException e) {

		}


		try {

			CinciaInt otherInt = (CinciaInt)other;
			return CinciaObject.wrap(value+otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaString otherStr = (CinciaString)other;
			return CinciaObject.wrap(value+otherStr.toJava());
		}catch (ClassCastException e) {

		}

		throw new RuntimeException("Unsupported addition!");

	}


	@Override
	public CinciaObject __sub__(CinciaObject other) {

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return CinciaObject.wrap(value-otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return CinciaObject.wrap(value-otherInt.toJava());
		}catch (ClassCastException e) {

		}


		throw new RuntimeException("Unsupported subtraction!");
	}


	@Override
	public CinciaObject __mul__(CinciaObject other) {


		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return CinciaObject.wrap(value*otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return CinciaObject.wrap(value*otherInt.toJava());
		}catch (ClassCastException e) {

		}


		throw new RuntimeException("Unsupported multiplication!");
	}

	@Override
	public CinciaObject __div__(CinciaObject other) {

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return CinciaObject.wrap(value/otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return CinciaObject.wrap(value/otherInt.toJava());
		}catch (ClassCastException e) {

		}

		throw new RuntimeException("Unsupported division!");
	}

	@Override
	public CinciaObject __mod__(CinciaObject other) {

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return CinciaObject.wrap(value%otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return CinciaObject.wrap(value%otherInt.toJava());
		}catch (ClassCastException e) {

		}


		throw new RuntimeException("Unsupported modulo!");
	}


	@Override
	public CinciaBool __eq__(CinciaObject other) {
		return (CinciaBool)CinciaObject.wrap(toJava().equals(other.toJava()));
	}

	@Override
	public Double toJava() {
		return value;
	}


}
