package com.luxlunaris.cincia.frontend.ast.expressions.type;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.interfaces.Stateful;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
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
		
//		System.out.println("comparing signatures, this: "+this+", other: "+other);


		try {

			Signature otherSig = (Signature)other;

			// null check the other
			if(otherSig == null ){//|| params == null ) {
				return false;
			}
			
			if(!matchParams(params, otherSig.params)) {
//				System.out.println("params don't match!");
				return false;
			}
			
//			System.out.println("params match!");

			// last but not least, check return type
			
			boolean returnTypeMatches =  matchReturnType(this.returnType, otherSig.returnType);
			
			
			if(!returnTypeMatches) {
				
//				System.out.println("return type doesn't match!");
//				System.out.println("this: "+this.returnType.getClass());
//				System.out.println("other: "+otherSig.returnType.getClass());
				
				
//				System.out.println();
			}
			
			return returnTypeMatches ;

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
	//??? But now ast knows about backend? Only expose interfaces
	// resolve eventual custom types within this Signature
	public Signature resolve(Eval eval, Enviro enviro) {		

		Signature signature = new Signature();
		
		signature.returnType = (Type) eval.eval(this.returnType, enviro);
		
		
		
		List<SingleDeclaration> oldParams = this.params==null? Arrays.asList() : this.params.toList();
		
		var newParams =	oldParams.stream().map(p->{ 
			
			var newType = (Type) eval.eval(p.getType(), enviro);
			p.changeType(newType);
			return p;
		
		}).collect(Collectors.toList());
		
		
		MultiDeclaration mD = new MultiDeclaration();
		mD.declarations = newParams;
		
		signature.params = mD.simplify();
		
	
		return signature;
	}



	@Override
	public Type unwrap() {
		return this;
	}


}
