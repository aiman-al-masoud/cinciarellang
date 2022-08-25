package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class SingleType implements Type{

	@Override
	public Expression simplify() {
		return this;
	}

	
//	@Override
//	public Object getValue() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Expression simplify() {
//		return this;
//	}

}
