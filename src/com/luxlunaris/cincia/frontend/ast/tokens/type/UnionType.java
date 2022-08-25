package com.luxlunaris.cincia.frontend.ast.tokens.type;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;

//TODO: add in EBNF
public class UnionType implements Type{
	
	public List<SingleType> types;
	
	public UnionType() {
		types = new ArrayList<SingleType>();
	}
	
	public void addType(SingleType sT) {
		types.add(sT);
	}
	
	@Override
	public Expression simplify() {
		return this;
	}
	
	@Override
	public String toString() {
		return types.stream().map(x->x+"").reduce((x1,x2)->x1+" | "+x2).get();
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
