package com.luxlunaris.cincia.frontend.preprocessor;

public class Preprocessor {
	
	protected String source;
	
	public Preprocessor(String source) {
		this.source = source;
	}
	
	public String process() {
		return this.source;
	}
	
}
