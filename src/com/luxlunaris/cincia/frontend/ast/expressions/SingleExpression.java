package com.luxlunaris.cincia.frontend.ast.expressions;

import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

public abstract class SingleExpression implements Expression {

	@Override
	public List<Expression> toList() {
		return Arrays.asList(this);
	}

}
