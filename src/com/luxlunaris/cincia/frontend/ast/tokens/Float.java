package com.luxlunaris.cincia.frontend.ast.tokens;

import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;

public class Float extends AbstractToken implements Constant{
	
	public final double value;
	
	public Float(double value) {
		this.value = value;
	}
}
