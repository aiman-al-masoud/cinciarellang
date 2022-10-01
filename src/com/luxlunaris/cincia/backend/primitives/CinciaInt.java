package com.luxlunaris.cincia.backend.primitives;

import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

//TODO: MAKE THIS ALSO A WRAPPER FOR LOOOOOOOONG
//TODO: implement comparison operators!!!!
//TODO: better error messages
//TODO: consider making true division the default, ie: i=1;i/=2;i==0.5
public class CinciaInt extends PrimitiveCinciaObject {

	private int value;

	public CinciaInt(int value) {
		super(new TypeWrapper(new PrimitiveType(PrimitiveType.INT)));
		this.value = value;	
		setImmutable();
	}
	
	@Override
	void setup() {	
		set("type", (CinciaObject)type);
	}


	@Override
	public CinciaObject __add__(CinciaObject other) {

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaInt(value+otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value+otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaString otherStr = (CinciaString)other;
			return new CinciaString(value+otherStr.toJava());
		}catch (ClassCastException e) {

		}


		// try the inverse
		try {
			return other.__add__(this);
		} catch (Exception e) {

		}

		throw new RuntimeException("Unsupported addition!");

	}


	@Override
	public CinciaObject __sub__(CinciaObject other) {

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaInt(value-otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value-otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		throw new RuntimeException("Unsupported subtraction!");
	}


	@Override
	public CinciaObject __mul__(CinciaObject other) {

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaInt(value*otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value*otherFloat.toJava());
		}catch (ClassCastException e) {

		}


		// try the inverse (multiplication is commutative)
		try {
			return other.__mul__(this);
		} catch (Exception e) {

		}

		throw new RuntimeException("Unsupported multiplication!");
	}

	@Override
	public CinciaObject __div__(CinciaObject other) {

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaInt(value/otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value/otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		throw new RuntimeException("Unsupported division!");
	}

	@Override
	public CinciaObject __mod__(CinciaObject other) {

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaInt(value%otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaFloat(value%otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		// try the inverse
		try {
			return other.__mod__(this);
		} catch (Exception e) {

		}

		throw new RuntimeException("Unsupported modulo!");
	}

	@Override
	public CinciaObject __lt__(CinciaObject other) {

		try {
			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaBool(value < otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {
			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaBool(value < otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		throw new RuntimeException();
	}

	@Override
	public CinciaObject as(List<CinciaObject> args) {

		CinciaObject type = args.get(0);

		try {
			CinciaKeyword kw = (CinciaKeyword)type;

			switch (kw.keyword) {
			case INT:
				return this;
			case FLOAT:
				return new CinciaFloat(this.value);
			case STRING:
				return new CinciaString(this.value+"");
			case BOOL:
				return __bool__();
			default:
				throw new RuntimeException("Type conversion not supported!");
			}

		}catch (ClassCastException e) {

		}

		return this;
	}


	@Override
	public CinciaBool __bool__() {
		return new CinciaBool(value!=0);
	}

	@Override
	public CinciaBool __eq__(CinciaObject other) {
		return new CinciaBool(toJava().equals(other.toJava()));
	}

	@Override
	public CinciaObject __lte__(CinciaObject other) {

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaBool(value <= otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaBool(value <= otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		throw new RuntimeException();
	}

	@Override
	public Integer toJava() {
		return value;
	}

	@Override
	public CinciaObject __neg__() {
		return new CinciaInt(-value);
	}

	@Override
	public CinciaObject __gt__(CinciaObject other) {


		try {

			CinciaInt otherInt = (CinciaInt)other;
			return new CinciaBool(value > otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return new CinciaBool(value > otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		throw new RuntimeException();
	}


}
