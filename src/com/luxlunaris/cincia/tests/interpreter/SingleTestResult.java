package com.luxlunaris.cincia.tests.interpreter;

import com.luxlunaris.cincia.tests.AnsiColors;

public class SingleTestResult {

	final static int SUCCESS = 0; // end condition satisfied
	final static int FAIL = 1; // end condition not satisfied
	final static int BROKEN = -1; // running the source throws an exception

	public String filename;
	public int outcome;
	public Exception exception;

	public SingleTestResult(String filename, int outcome) {
		this.filename = filename;
		this.outcome = outcome;
	}
	
	
	public SingleTestResult(String filename, int outcome, Exception exception) {
		this.filename = filename;
		this.outcome = outcome;
		this.exception = exception;
	}
	
	@Override
	public String toString() {
		return filename+" "+(outcome==0? AnsiColors.ok("PASS") : outcome>0? AnsiColors.fail("FAIL") : AnsiColors.warn("BROKEN"));
	}

}
