package com.luxlunaris.cincia.frontend.ast.tokens.keyword;

import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

public class Keyword extends AbstractToken{

	public final Keywords value;
	
	public Keyword(Keywords value) {
		this.value = value;
	}
}
