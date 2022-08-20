package com.luxlunaris.cincia.frontend.nodes.expressions.postfix;

import com.luxlunaris.cincia.frontend.nodes.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;
import com.luxlunaris.cincia.frontend.nodes.interfaces.PostfixExpression;

public class CalledExpression implements PostfixExpression {
	
	public PostfixExpression callable;
	public MultiExpression args;
	
}
