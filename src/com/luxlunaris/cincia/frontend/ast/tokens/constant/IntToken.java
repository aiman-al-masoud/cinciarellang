package com.luxlunaris.cincia.frontend.ast.tokens.constant;

import com.luxlunaris.cincia.frontend.ast.interfaces.Constant;
import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

public class IntToken extends AbstractToken implements Constant{
	
	public final int value;
	
	public IntToken(int value) {
		this.value = value;
	}
}
