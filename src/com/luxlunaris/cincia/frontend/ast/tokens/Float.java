package com.luxlunaris.cincia.frontend.ast.tokens;

public class Float extends AbstractToken{
	
	public final double value;
	
	public Float(double value) {
		this.value = value;
	}
}
