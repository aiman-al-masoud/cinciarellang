package com.luxlunaris.cincia.frontend.ast.tokens.constant;

import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;
import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

public class Str extends AbstractToken implements Constant{
	
	public final String value;
	
	public Str(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "'"+value+"'";
	}
}
