package com.luxlunaris.cincia.frontend.ast.expressions.postfix;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;


/**
 * Right associative 
 * (I need to wait to scroll all the way to the right, to get 
 * to the attribute I'm seeking).
 *
 */
public class DotExpression implements PostfixExpression, LeftValue {
	
	public PostfixExpression left; //dottable
	public DotExpression right;
	
	@Override
	public String toString() {
		return "("+left+"."+right+")";
	}
	
}
