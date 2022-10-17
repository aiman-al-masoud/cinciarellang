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
public class CinciaInt extends PrimitiveCinciaObject {

	protected int value;	

	public CinciaInt(int value) {
		this.value = value;		
		this.type = new CinciaInt();
		set(Magic.type, (CinciaObject)this.type); //TODO: looks like this works too, because comparison in matches is done with getClass()
		isInstance = true;
		setImmutable();		
	}

	public CinciaInt() {
		isInstance = false;
	}

	@Override
	public String toString() {
		return isInstance? toJava()+"" : Keywords.INT.toString();		
	}











	@Override
	public CinciaObject __add__(CinciaObject other) {

		try {
			return CinciaObject.wrap(Alu.perform(this.toJava(), other.toJava(), Operators.PLUS));
		} catch (Exception e) {

		}

		try {

			CinciaString otherStr = (CinciaString)other;
			return CinciaObject.wrap(toJava()+otherStr.toJava());			
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

		if(type.matches(new CinciaBool())) {
			return __bool__();
		}

		if(type.matches(new CinciaInt())) {
			return this;
		}

		if(type.matches(new CinciaFloat())) {
			return new CinciaFloat(toJava());
		}

		if(type.matches(CinciaString.myClass)) {
			return new CinciaString(toJava()+"");
		}

		throw new RuntimeException("Type conversion"+this.getType()+" to "+type+" not supported!");
	}


}
