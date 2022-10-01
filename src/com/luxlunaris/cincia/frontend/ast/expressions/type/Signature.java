package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.interfaces.Stateful;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

/**
 * The abstract signature of a lambda function.
 * 
 * signature ::= '\' [multi_declaration] [':' type]  
 */
public class Signature implements Type{

	public Declaration params; // can be null if func takes no args
	public Type returnType; 

	public Signature() {
		returnType = Type.Any;
	}

	@Override
	public String toString() {
		return "\\" + params +" -> "+returnType;
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

			// null check the other
			if(otherSig == null ){//|| params == null ) {
				return false;
			}

			if(!matchParams(params, otherSig.params)) {
				return false;
			}

			// last but not least, check return type
			return matchReturnType(returnType, otherSig.returnType);

		} catch (ClassCastException e) {

		}

		return false;

	}


	protected boolean matchParams(Declaration params1, Declaration params2) {

		// both this and other don't take params?
		if(params1 == null && params2 == null) {
			return true;
		}

		// read as an exor
		if(params1 == null || params2 == null) {
			return false;
		}

		// both not null ...

		// unequal number of params
		if( params1.toList().size() != params2.toList().size()) {
			return false;
		}

		// all positional param types must match
		for(int i=0; i < params1.toList().size(); i++) {
			Type thisType = params1.toList().get(i).getType();
			Type otherType = params2.toList().get(i).getType();
			if (!thisType.matches(otherType)) return false; // order matters, reference can be more general than assigned value
		}

		return true;
	}

	protected boolean matchReturnType(Type retype1, Type retype2) {		
		return retype1.matches(retype2);
	}

	//TODO: to type wrapper
	//??? But now ast knows about backend? But only interfaces
	//	public Object toCincia(Eval eval, Stateful enviro) {
	//		
	//	}






}
