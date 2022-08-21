package com.luxlunaris.cincia.frontend.ast.expressions.postfix;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;

public class IndexedExpression implements PostfixExpression, LeftValue{
	
	public PostfixExpression indexable;
	public Expression indexOrIterable;
	
}
