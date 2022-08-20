package com.luxlunaris.cincia.frontend.ast.tokens.punctuation;

import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

public class Punctuation extends AbstractToken{
	
	public final Punctuations value;
	
	public Punctuation(Punctuations value) {
		this.value = value;
	}
}
