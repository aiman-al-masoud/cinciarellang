package com.luxlunaris.cincia.frontend.ast.tokens.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class SingleType implements Type{

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression simplify() {
		return this;
	}

}
