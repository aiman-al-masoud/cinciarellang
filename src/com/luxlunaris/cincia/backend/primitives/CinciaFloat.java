package com.luxlunaris.cincia.backend.primitives;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

//TODO: better error messages
public class CinciaFloat extends CinciaNumber {

	protected double value;

	public CinciaFloat(double value) {
		super(new TypeWrapper(new PrimitiveType(PrimitiveType.FLOAT)));
		this.value = value;		
	}
//
//	@Override
//	public CinciaObject __add__(CinciaObject other) {
//		return CinciaObject.wrap( Alu.perform(this.toJava(), other.toJava(), Operators.PLUS) );
//	}
//
//	@Override
//	public CinciaObject __mul__(CinciaObject other) {
//		return CinciaObject.wrap( Alu.perform(this.toJava(), other.toJava(), Operators.ASTERISK) );
//	}
//
//	@Override
//	public CinciaObject __mod__(CinciaObject other) {
//		return CinciaObject.wrap( Alu.perform(this.toJava(), other.toJava(), Operators.MOD) );
//	}
//	
	@Override
	public Double toJava() {
		return value;
	}

}
