package com.luxlunaris.cincia.frontend.ast.expressions.postfix;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;

/**
 * Right assoc
 *
 */
public class IndexedExpression implements PostfixExpression, LeftValue{
	
	public PostfixExpression indexable;
	public Expression index; //can also be iterable
	
//	public IndexedExpression indexOrIterable;
	
	
	
}
