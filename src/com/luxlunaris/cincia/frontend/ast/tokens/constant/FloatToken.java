package com.luxlunaris.cincia.frontend.ast.tokens.constant;

import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;
import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

public class FloatToken extends AbstractToken implements Constant{
	
	public final double value;
	
	public FloatToken(double value) {
		this.value = value;
	}
}
