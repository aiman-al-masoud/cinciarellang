package com.luxlunaris.cincia.frontend.ast.expressions.postfix;

import java.util.concurrent.Callable;

//import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;

public class CalledExpression implements PostfixExpression {
	
	public PostfixExpression callable;
	public Expression args;
	
	@Override
	public Expression simplify() {
		CalledExpression cE = new CalledExpression();
		cE.callable = (PostfixExpression) callable.simplify();
		
		if(cE.args != null) {
			cE.args = args.simplify();
		}
		
		return cE;
	}
	
	@Override
	public String toString() {
		return callable+"("+args+")";
	}
}
