package com.luxlunaris.cincia.frontend.ast.expressions.postfix;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;

/**
 * 
 * Stuff like x++, x--, x+=1, x/=2 ...
 *
 */
public class ReassignmentExpression implements PostfixExpression{
	
	public Operators operator;
	public PostfixExpression left;
	public Expression right; //could be null
	
	
}
