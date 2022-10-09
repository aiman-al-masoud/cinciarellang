package com.luxlunaris.cincia.frontend.ast.expressions.postfix;

import com.luxlunaris.cincia.frontend.ast.expressions.SingleExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;

/**
 * Right assoc
 *
 */
public class IndexedExpression extends SingleExpression implements PostfixExpression, LeftValue{
	
	public PostfixExpression indexable;
	public Expression index; //can also be iterable
	
	@Override
	public Expression simplify() {
		this.indexable = (PostfixExpression) indexable.simplify();
		this.index = index.simplify();
		return this;
	}
	
//	public IndexedExpression indexOrIterable;
	
	@Override
	public String toString() {
		return indexable+"["+index+"]";
	}
	
	
}
