package com.luxlunaris.cincia.frontend.ast.expressions.postfix;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.expressions.SingleExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;

public class CalledExpression extends SingleExpression implements PostfixExpression {

	public PostfixExpression callable;
	public List<Expression> args;

	public CalledExpression() {
		args = new ArrayList<>();
	}

	@Override
	public Expression simplify() {
		CalledExpression cE = new CalledExpression();
		cE.callable = (PostfixExpression) callable.simplify();
		cE.args = args.stream().map(e->e.simplify()).collect(Collectors.toList());
		return cE;
	}

	@Override
	public String toString() {
		return callable+"("+args+")";
	}
}
