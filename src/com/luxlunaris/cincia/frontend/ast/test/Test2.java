package com.luxlunaris.cincia.frontend.ast.test;

import com.luxlunaris.cincia.frontend.ast.expressions.postfix.DotExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class Test2 {
	
	public static void main(String[] args) {
		
		
		
		// inner (rightmost) attribute
		DotExpression dexp3 = new DotExpression();
		dexp3.left = new Identifier("z");
		dexp3.right = null;

		DotExpression dexp2 = new DotExpression();
		dexp2.left  = new Identifier("y");
		dexp2.right = dexp3;
		
		
		DotExpression dexp = new DotExpression();
		dexp.left = new Identifier("x");
		dexp.right = dexp2;
		
		// x.y.z which really is (z.(y.(z)))
		System.out.println(dexp);
		
	}

}
