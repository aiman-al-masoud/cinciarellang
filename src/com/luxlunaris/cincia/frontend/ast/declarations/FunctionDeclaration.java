package com.luxlunaris.cincia.frontend.ast.declarations;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;

/**
 * A named function declaration, such as those in interfaces.
 *
 */
public class FunctionDeclaration extends SingleDeclaration {
	
	public List<Modifier> modifiers;
	public Identifier name;
	public Signature signature;
	
	public FunctionDeclaration() {
		this.modifiers = new ArrayList<Modifier>();
	}
	
	@Override
	public Declaration simplify() {
		return this;
	}
	
	@Override
	public String toString() {
		return "("+modifiers+" "+name+" "+signature+")";
	}
	
}
