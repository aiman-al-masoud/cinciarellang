package com.luxlunaris.cincia.frontend.nodes.tokens.keyword;

import com.luxlunaris.cincia.frontend.nodes.tokens.AbstractToken;

public class Keyword extends AbstractToken{

	public final Keywords value;
	
	public Keyword(Keywords value) {
		this.value = value;
	}
}
