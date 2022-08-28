package com.luxlunaris.cincia.frontend.parser.test;

import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;

public class InterfaceTester extends AbstractTester {

	public InterfaceTester() {
		
		ClassExpression cE = new ClassExpression();


		// interface 
		add("interface { x:int; y:int; f:\\x:int:int;  };", "");

		
		
		
		
		
	}

}
