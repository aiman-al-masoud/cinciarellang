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

//TODO: MAKE THIS ALSO A WRAPPER FOR LOOOOOOOONG
//TODO: better error messages
//TODO: consider making true division the default, ie: i=1;i/=2;i==0.5
public class CinciaInt extends PrimitiveCinciaObjectNewVersion {

	protected int value;
	public static final CinciaInt myClass = new CinciaInt();
	

	public CinciaInt(int value) {
		this.value = value;		
		this.type = myClass;
		set(Magic.type, myClass);
		isInstance = true;
		setImmutable();		
	}
	
	public CinciaInt() {
		isInstance = false;
	}
	
	@Override
	public String toString() {
		return isInstance? value+"" : Keywords.INT.toString();		
	}
	

	
	
	
	
	
	

	
	
	@Override
	public CinciaObject __add__(CinciaObject other) {

		try {
			return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.PLUS));
		} catch (Exception e) {

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
	public CinciaObject __mul__(CinciaObject other) {

		try {
			return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.ASTERISK));
		} catch (Exception e) {

		}

		// try the inverse (multiplication is commutative)
		try {
			return other.__mul__(this);
		} catch (Exception e) {

		}

		throw new RuntimeException("Unsupported multiplication!");
	}


	@Override
	public CinciaObject __mod__(CinciaObject other) {

		try {
			return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.MOD));
		} catch (Exception e) {

		}

		// try the inverse
		try {
			return other.__mod__(this);
		} catch (Exception e) {

		}

		throw new RuntimeException("Unsupported modulo!");
	}

	@Override
	public Integer toJava() {
		return value;
	}

	@Override
	public CinciaObject __neg__() {
		return CinciaObject.wrap(-toJava());
	}

	@Override
	public CinciaObject as(List<CinciaObject> args) {

		Type type = (Type) args.get(0);	//TODO: classcast exception

//		if(type.matches(new PrimitiveType(PrimitiveType.INT))) {
//			return this;
//		}

		if(type.matches(new PrimitiveType(PrimitiveType.FLOAT))) {
			return CinciaObject.wrap((double)this.value);
		}

		if(type.matches(new PrimitiveType(PrimitiveType.STRING))) {
			return CinciaObject.wrap(this.value+"");
		}

		if(type.matches(CinciaBool.myClass)) {
			return __bool__();
		}

		throw new RuntimeException("Type conversion"+this.getType()+" to "+type+" not supported!");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
