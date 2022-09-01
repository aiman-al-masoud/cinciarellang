package com.luxlunaris.cincia.frontend.ast.expressions.type;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;

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
		this.params = params.simplify();
		this.returnType = (Type) returnType.simplify();
		return this;
	}
	
}
