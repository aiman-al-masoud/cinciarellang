package com.luxlunaris.cincia.frontend.ast.expressions.postfix;

//import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;

public class CalledExpression implements PostfixExpression {
	
	public PostfixExpression callable;
	public Expression args;
	
}
