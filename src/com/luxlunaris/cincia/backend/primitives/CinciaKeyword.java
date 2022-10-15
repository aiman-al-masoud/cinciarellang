package com.luxlunaris.cincia.backend.primitives;

import com.luxlunaris.cincia.backend.object.BaseCinciaObject;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class CinciaKeyword extends BaseCinciaObject{

	public Keywords keyword;

	public CinciaKeyword(Keywords keyword) {
		super(new PrimitiveType(keyword));
		this.keyword = keyword;
	}
	
	@Override
	public String toString() {
		return keyword.toString();
	}
	
	@Override
	public Object toJava() {
		return keyword;
	}

}
