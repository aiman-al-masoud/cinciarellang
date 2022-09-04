package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class CinciaKeyword extends PrimitiveCinciaObject{

	private Keywords keyword;

	public CinciaKeyword(Keywords keyword) {
		super(new PrimitiveType(keyword));
		this.keyword = keyword;
	}

	@Override
	public Object getValue() {
		return keyword;
	}
	
	@Override
	public String toString() {
		return keyword.toString();
	}

}
