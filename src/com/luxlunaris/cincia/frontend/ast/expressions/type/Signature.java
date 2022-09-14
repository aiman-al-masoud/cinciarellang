package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

/**
 * The abstract signature of a lambda function.
 * 
 * signature ::= '\' [multi_declaration] [':' type]  
 */
public class Signature implements Type{

	public Declaration params;// can be null if func takes no args
	public Type returnType;

	@Override
	public String toString() {
		return "\\" + params +" : "+returnType;
	}

	@Override
	public Signature simplify() { //TODO: how does THIS work? Auto upcast?

		if(params!=null) {
			this.params = params.simplify();
		}

		if(returnType!=null) {
			this.returnType = (Type) returnType.simplify();
		}

		return this;
	}

	@Override
	public boolean matches(Type other) {

		try {

			Signature otherSig = (Signature)other;

			// unequal number of params
			if(params.toList().size() != otherSig.params.toList().size()) {
				return false;
			}

			// all positional param types must match
			for(int i=0; i < params.toList().size(); i++) {
				Type thisType = params.toList().get(i).getType();
				Type otherType = otherSig.params.toList().get(i).getType();
				if (!thisType.matches(otherType)) return false; // order matters, reference can be more general than assigned value
			}

			// lastly check return type
			
			if(returnType!=null && otherSig.returnType!=null) {
				return returnType.matches(otherSig.returnType);				
			}
			

		} catch (ClassCastException e) {

		}

		return false;

	}

}
