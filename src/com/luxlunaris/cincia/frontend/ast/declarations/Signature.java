package com.luxlunaris.cincia.frontend.ast.declarations;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;

/**
 * The abstract signature of a lambda function.
 * 
 * signature ::= modifier* '\' [multi_declaration] [':' type]  
 */
public class Signature implements Declaration{
	
	public List<Modifier> modifiers; // can be empty
	public Declaration params;// can be null if func takes no args
	public Type returnType;
	
	public Signature() {
		modifiers = new ArrayList<Modifier>();
	}
	
	public void addModifier(Modifier modifier) {
		modifiers.add(modifier);
	}
}
