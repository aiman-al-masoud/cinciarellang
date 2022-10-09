package com.luxlunaris.cincia.frontend.ast.expressions.type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.frontend.ast.expressions.SingleExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.AbstractToken;
import com.sun.source.tree.Tree;

//TODO: add in EBNF
public class UnionType extends SingleExpression implements Type{

	public List<Type> types;

	public UnionType() {
		types = new ArrayList<Type>();
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
		
		var other2 = other.unwrap();
		
		// if other is another union
		try {

			UnionType otherUnion = (UnionType)other2;

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
		return types.stream().anyMatch(t->t.matches(other2));
	}
	
	@Override
	public Type unwrap() {
		return this;
	}
	
	

	@Override
	public Type resolve(Eval eval, Enviro enviro) {
		UnionType u = new UnionType();
		u.types =  this.types.stream().map(t-> (Type) eval.eval(t, enviro)).collect(Collectors.toList());
		return u;
	}

}
