package com.luxlunaris.cincia.tests.parser.classes;

public class JumpTester extends AbstractTester {

	public JumpTester() {
		
		add("while x{ break;  }", "");
		add("while x{ continue;  }", "");

	}
}
