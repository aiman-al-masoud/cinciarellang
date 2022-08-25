package com.luxlunaris.cincia.frontend.ast.test;

import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;


public class Test {

	// z = y = x = 1
	// (z = (y = (x = 1)))
	public static void main(String[] args) {
		
		
		AssignmentExpression asgnNested2 = new AssignmentExpression();
		asgnNested2.left = new Identifier("x");
		asgnNested2.right = new Int(1);
		
		AssignmentExpression asgnNested1 = new AssignmentExpression();
		asgnNested1.left = new Identifier("y");
		asgnNested1.right = asgnNested2;
		
		AssignmentExpression asgn = new AssignmentExpression();
		asgn.left  = new Identifier("z");
		asgn.right = asgnNested1;
		
		
		System.out.println(asgn);

	}

}
