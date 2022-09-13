package com.luxlunaris.cincia.frontend.ast.expressions.type;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;
import com.sun.source.tree.Tree;

//TODO: add in EBNF
public class UnionType implements Type{
	
	public List<SingleType> types;
	
	public UnionType() {
//		System.out.println("creating union type");
		types = new ArrayList<SingleType>();
	}
	
	public void addType(SingleType sT) {
		types.add(sT);
	}
	
	@Override
	public Expression simplify() {
				
		if(types.size()==1) {
			return types.get(0);
		}
		
		return this;
	}
	
	@Override
	public String toString() {
		return types.stream().map(x->x+"").reduce((x1,x2)->x1+" | "+x2).get();
	}

	@Override
	public boolean matches(Type other) {
		
		//TODO!!!!!!!!
//		types.size() == 1
		System.out.println(types);
		return types.stream().anyMatch(t->t.matches(other));
//		return true;
	}
	
}
