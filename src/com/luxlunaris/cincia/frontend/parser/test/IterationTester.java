package com.luxlunaris.cincia.frontend.parser.test;

public class IterationTester extends AbstractTester {

	public IterationTester() {
		add("for i in x{ x = do(x); x+=1;  }", "");
		add("while x{ x = do(x); x+=1;  }", "");
	}

}
