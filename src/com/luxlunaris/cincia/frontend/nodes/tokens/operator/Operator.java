package com.luxlunaris.cincia.frontend.nodes.tokens.operator;

import com.luxlunaris.cincia.frontend.nodes.tokens.AbstractToken;

public class Operator extends AbstractToken{
	
	public final Operators value;
	
	public Operator(Operators value) {
		this.value = value;
	}
	
}
