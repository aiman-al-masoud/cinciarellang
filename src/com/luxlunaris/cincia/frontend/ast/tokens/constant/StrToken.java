package com.luxlunaris.cincia.frontend.ast.tokens.constant;

import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;
import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

public class StrToken extends AbstractToken implements Constant{
	
	public final String value;
	
	public StrToken(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "'"+value+"'";
	}
}
