package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class CinciaKeyword extends CinciaObject{

	private Keywords keyword;

	public CinciaKeyword(Keywords keyword) {
		super(null);
	}

	@Override
	public Object getValue() {
		return keyword;
	}

}
