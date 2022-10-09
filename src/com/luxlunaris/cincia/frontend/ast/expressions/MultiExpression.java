package com.luxlunaris.cincia.frontend.ast.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

/**
 * Sequence of comma-separated single expressions		
 * 
 *
 */
public class MultiExpression implements Expression{

	public List<Expression> expressions;

	public MultiExpression() {
		expressions = new ArrayList<Expression>();
	}

	public void addExpression(Expression expression) {
		expressions.add(expression);
	}

	@Override
	public Expression simplify() {

		MultiExpression mE = new MultiExpression();
		mE.expressions = expressions.stream().map(e->e.simplify()).collect(Collectors.toList());
		return mE.expressions.size()==1? mE.expressions.get(0) : mE;
	}

	@Override
	public String toString() {
		return expressions.toString();
	}

	@Override
	public List<Expression> toList() {
		return expressions;
	}

}
