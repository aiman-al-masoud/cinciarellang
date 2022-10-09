package com.luxlunaris.cincia.backend.throwables;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class TypeError extends CinciaException {

	public String lvalue;
	public Type expected;
	public Type got;
	public String message;

	public TypeError(String msg) {
		super(msg);
		this.message = msg;
	}

	public TypeError() {
		/* on purpose */
	}

	@Override
	public String toString() {

		if(message!=null) {
			return message;
		}

		return "TypeError: '"+lvalue+"' expected type: '"+expected+"', but got: '"+got+"'";
	}

}
