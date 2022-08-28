package com.luxlunaris.cincia.frontend.parser.test;

public class JumpTester extends AbstractTester {

	public JumpTester() {
		
		add("while x{ break;  }", "");
		add("while x{ continue;  }", "");

	}
}
