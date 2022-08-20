package com.luxlunaris.cincia.frontend.nodes.expressions.postfix;

import com.luxlunaris.cincia.frontend.nodes.expressions.Expression;
import com.luxlunaris.cincia.frontend.nodes.expressions.MultiExpression;

public class CalledExpression implements PostfixExpression {
	
	public PostfixExpression callable;
	public MultiExpression args;
	
}
