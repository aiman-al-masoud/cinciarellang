package com.luxlunaris.cincia.frontend.nodes.expressions.postfix;

import com.luxlunaris.cincia.frontend.nodes.interfaces.Expression;
import com.luxlunaris.cincia.frontend.nodes.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.nodes.interfaces.PostfixExpression;

public class IndexedExpression implements PostfixExpression , LeftValue{
	
	public PostfixExpression indexable;
	public Expression indexOrIterable;
	
}
