package com.luxlunaris.cincia.backend;

public class SingleTestResult {

	final static int SUCCESS = 0;
	final static int FAIL = 1;
	final static int BROKEN = -1;

	public String filename;
	public int outcome;

	public SingleTestResult(String filename, int outcome) {
		this.filename = filename;
		this.outcome = outcome;
	}

}
