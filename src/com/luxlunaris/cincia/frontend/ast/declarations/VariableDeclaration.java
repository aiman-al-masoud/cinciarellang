package com.luxlunaris.cincia.frontend.ast.declarations;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;

/**
 * 
 * Name, type, and modifers of a (possibly uninitialized) variable.
 * modifiers can be empty, type can be null if inferred.
 *
 */
public class VariableDeclaration extends SingleDeclaration{
	
	public List<Modifier> modifiers;
	public Identifier name;
	public Type type; 
	
	public VariableDeclaration() {
		modifiers = new ArrayList<Modifier>();
	}
	
	public void addModifier(Modifier modifier) {
		modifiers.add(modifier);
	}
	
}
