package com.luxlunaris.cincia.backend.primitives;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.Magic;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

//TODO: better error messages
public class CinciaFloat extends PrimitiveCinciaObject {

	protected double value;
	

	public CinciaFloat(double value) {
		this.value = value;		
		this.type = new CinciaFloat();
		set(Magic.type, (CinciaObject) this.type);
		isInstance = true;
		setImmutable();		
	}
	
	public CinciaFloat() {
		isInstance = false;
	}
	
	@Override
	public String toString() {
		return isInstance? toJava()+"" : Keywords.FLOAT.toString();		
	}
	
	@Override
	public Double toJava() {
		return value;
	}
	
	@Override
	public CinciaObject __neg__() {
		return CinciaObject.wrap(-toJava());
	}

}
