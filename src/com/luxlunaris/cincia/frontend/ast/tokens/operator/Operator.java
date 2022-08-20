package com.luxlunaris.cincia.frontend.ast.tokens.operator;

import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

public class Operator extends AbstractToken{
	
	public final Operators value;
	
	public Operator(Operators value) {
		this.value = value;
	}
	
}
