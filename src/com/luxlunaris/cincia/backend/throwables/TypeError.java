package com.luxlunaris.cincia.backend.throwables;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class TypeError extends CinciaException {
	
	public String lvalue;
	public Type expected;
	public Type got;
	
	@Override
	public String toString() {
		return "left value: '"+lvalue+"', of expected type: '"+expected+"', got type: '"+got+"'";
	}

}
