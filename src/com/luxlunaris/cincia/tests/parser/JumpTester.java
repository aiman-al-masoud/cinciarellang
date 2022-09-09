package com.luxlunaris.cincia.tests.parser;

public class JumpTester extends AbstractTester {

	public JumpTester() {
		
		add("while x{ break;  }", "");
		add("while x{ continue;  }", "");

	}
}
