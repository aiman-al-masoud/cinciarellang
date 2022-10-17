package com.luxlunaris.cincia.backend.primitives;

import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.CinciaCinciaClass;
import com.luxlunaris.cincia.backend.object.Magic;
import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

public class CinciaBool extends PrimitiveCinciaObject {

	private boolean value;
	
	
	public CinciaBool(boolean value) {
		this.value = value;
		this.type = new CinciaBool();
		set(Magic.type, (CinciaObject)this.type);
		isInstance = true;
		setImmutable();
	}
	
	protected CinciaBool() {
		isInstance = false;
	}
	
	@Override
	public String toString() {
		return isInstance? toJava()+"" : Keywords.BOOL.toString();		
	}
	

	
	
	
	
	
	
	

	@Override
	public CinciaBool __bool__() {
		return this;
	}

	@Override
	public CinciaObject __or__(CinciaObject other) {
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.OR));
	}

	@Override
	public CinciaObject __and__(CinciaObject other) {
		return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.AND));
	}
	
	@Override
	public CinciaBool __eq__(CinciaObject other) {
		return (CinciaBool) CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.COMPARE));
	}

	@Override
	public CinciaBool __ne__(CinciaObject other) {
		return (CinciaBool) CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.NE));
	}

	@Override
	public CinciaBool __not__() {
		return new CinciaBool(!toJava());
	}

	@Override
	public CinciaString __str__() {
		return new CinciaString(toJava()+"");
	}
	
	@Override
	public CinciaObject as(List<CinciaObject> args) {//TODO:!!!
		throw new RuntimeException("CinciaBool.as() not implemented!");
	}

	@Override
	public Boolean toJava() {
		return value;
	}

}
