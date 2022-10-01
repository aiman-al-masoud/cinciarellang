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

		// if other is another union
		try {

			UnionType otherUnion = (UnionType)other;

			//TODO: make sure the 2 lists of simple types are sorted in the same order
			for(int i=0; i < otherUnion.types.size(); i++) {
				if (!otherUnion.types.get(i).matches(types.get(i))) {
					return false;
				}
			}

			return true;
		} catch (ClassCastException e) {

		}


		// if other is a simple type
		return types.stream().anyMatch(t->t.matches(other));
	}
	
	@Override
	public Type unwrap() {
		return this;
	}

}
