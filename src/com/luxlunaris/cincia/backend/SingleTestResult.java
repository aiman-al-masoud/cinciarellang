package com.luxlunaris.cincia.backend;

public class SingleTestResult {

	final static int SUCCESS = 0;
	final static int FAIL = 1;
	final static int BROKEN = -1;

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
