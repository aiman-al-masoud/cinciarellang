package com.luxlunaris.cincia.frontend.parser.test;

public class SelectionTester extends AbstractTester {

	public SelectionTester() {
		add("if x { x = 1; y = 2;}else{y = x = 0; }", "");
		add("match x{case 1:return 1;case 2:return 2;default:return 0;}", "");
	}
}
