package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public abstract class SingleType implements Type{

	@Override
	public Expression simplify() {
		return this;
	}

}
