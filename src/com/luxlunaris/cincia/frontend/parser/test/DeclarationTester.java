package com.luxlunaris.cincia.frontend.parser.test;

public class DeclarationTester extends AbstractTester{

	public DeclarationTester() {
		// types/declarations
		add("x:int", "");
		add("x:int|float|Object", "");
		add("x:{int:int}", "");
		add("x:int[]", "");
		add("f:\\x:int:int;", "");
	}

}
