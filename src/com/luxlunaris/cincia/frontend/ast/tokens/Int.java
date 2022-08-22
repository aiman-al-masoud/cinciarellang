package com.luxlunaris.cincia.frontend.ast.tokens;

import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;

public class Int extends AbstractToken implements Constant{
	
	public final int value;
	
	public Int(int value) {
		this.value = value;
	}
}
