package com.luxlunaris.cincia.frontend.parser.test;

public class ExceptionTester extends AbstractTester {
	
	public ExceptionTester() {
		add("throw 1+1;", "");
		add("try { 1/0; }catch e:String|char { return 1; }", "");
		add("try { 1/0; }catch e:float { }", "");
		add("try { 1/0; }catch e:int { }", "");
	}
}
