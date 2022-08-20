package com.luxlunaris.cincia.frontend.ast.declarations;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;

/**
 * 
 * Name, type, and modifers of a (possibly uninitialized) variable.
 * Modifiers can be empty, type can be null if inferred.
 *
 */
public class SingleDeclaration implements Declaration{
	
	public List<Modifier> modifiersList;
	public Identifier identifier;
	public Identifier type; //TODO: turn primitive types into identifiers or better yet make Type class so as to include also symbols and "generics"
	
	public SingleDeclaration() {
		modifiersList = new ArrayList<Modifier>();
	}
	
}
