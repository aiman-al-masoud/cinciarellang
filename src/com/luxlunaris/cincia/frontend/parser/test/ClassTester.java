package com.luxlunaris.cincia.frontend.parser.test;

public class ClassTester extends AbstractTester {

	public ClassTester() {
		// class with declarations, methods and assignments
		add("class { x:int; f:\\x:int:int; f = \\x->1; x = 1;  };", "");

	}
}
