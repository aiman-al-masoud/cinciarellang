package com.luxlunaris.cincia.frontend.ast.expressions.postfix;

import com.luxlunaris.cincia.frontend.ast.expressions.SingleExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;


/**
 * Right associative 
 * (I need to wait to scroll all the way to the right, to get 
 * to the attribute I'm seeking).
 *
 */
public class DotExpression extends SingleExpression implements PostfixExpression, LeftValue {
	
	public PostfixExpression left; //dottable
	public Identifier right;
	
	@Override
	public String toString() {
		return "("+left+"."+right+")";
	}

	@Override
	public Expression simplify() {
		this.left = (PostfixExpression) left.simplify();
		return this;
	}
	
}
