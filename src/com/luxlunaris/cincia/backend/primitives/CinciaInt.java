package com.luxlunaris.cincia.backend.primitives;

import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

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
	public CinciaObject __add__(CinciaObject other) {

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return CinciaObject.wrap(value+otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return CinciaObject.wrap(value+otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaString otherStr = (CinciaString)other;
			return CinciaObject.wrap(value+otherStr.toJava());			
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
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.MINUS));
	}

	@Override
	public CinciaObject __mul__(CinciaObject other) {

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return CinciaObject.wrap(value*otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return CinciaObject.wrap(value*otherFloat.toJava());
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
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.DIV));
	}

	@Override
	public CinciaObject __mod__(CinciaObject other) {

		try {

			CinciaInt otherInt = (CinciaInt)other;
			return CinciaObject.wrap(value%otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return CinciaObject.wrap(value%otherFloat.toJava());
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
			return CinciaObject.wrap(value < otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {
			CinciaFloat otherFloat = (CinciaFloat)other;
			return CinciaObject.wrap(value < otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		throw new RuntimeException();
	}

	@Override
	public CinciaObject as(List<CinciaObject> args) {

		Type type = (Type) args.get(0);	//TODO: classcast exception

		if(type.matches(new PrimitiveType(PrimitiveType.INT))) {
			return this;
		}

		if(type.matches(new PrimitiveType(PrimitiveType.FLOAT))) {
			return CinciaObject.wrap((double)this.value);
		}

		if(type.matches(new PrimitiveType(PrimitiveType.STRING))) {
			return CinciaObject.wrap(this.value+"");
		}

		if(type.matches(new PrimitiveType(PrimitiveType.BOOL))) {
			return __bool__();
		}

		throw new RuntimeException("Type conversion"+this.getType()+" to "+type+" not supported!");
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
		return CinciaObject.wrap(-value);
	}

	@Override
	public CinciaObject __gt__(CinciaObject other) {


		try {

			CinciaInt otherInt = (CinciaInt)other;
			return CinciaObject.wrap(value > otherInt.toJava());
		}catch (ClassCastException e) {

		}

		try {

			CinciaFloat otherFloat = (CinciaFloat)other;
			return CinciaObject.wrap(value > otherFloat.toJava());
		}catch (ClassCastException e) {

		}

		throw new RuntimeException();
	}
	
	@Override
	public CinciaString __str__() {
		return (CinciaString) CinciaObject.wrap(value+"");
	}


}
