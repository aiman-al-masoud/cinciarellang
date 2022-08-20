package com.luxlunaris.cincia.frontend.ast.test;

import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.DotExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.Str;

public class Test3 {
	
	public static void main(String[] args) {

		// inner (rightmost) attribute
		DotExpression dexp3 = new DotExpression();
		dexp3.left = new Identifier("z");
		dexp3.right = null;

		DotExpression dexp2 = new DotExpression();
		dexp2.left  = new Identifier("y");
		dexp2.right = dexp3;	
		
		AssignmentExpression asgn =  new AssignmentExpression();
		
		asgn.left = dexp2;
		asgn.right = new Str("ciao mondo");
		System.out.println(asgn);
	}
	
	
}
