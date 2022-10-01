package com.luxlunaris.cincia.frontend.ast.declarations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

/**
 * A named function declaration, such as those in interfaces.
 *
 */
public class FunctionDeclaration extends SingleDeclaration {
	
	public List<Modifiers> modifiers;
	public Identifier name;
	public Signature signature;
	
	public FunctionDeclaration() {
		this.modifiers = new ArrayList<Modifiers>();
	}
	
	@Override
	public Declaration simplify() {
		this.signature = signature.simplify();
		return this;
	}
	
	@Override
	public String toString() {
		return "("+modifiers+" "+name+" "+signature+")";
	}
	
	@Override
	public String getName() {
		return name.value;
	}

	@Override
	public Type getType() {
		return signature;
	}

	@Override
	public List<Modifiers> getModifiers() {
		return modifiers;
	}

	@Override
	public List<SingleDeclaration> toList() {
		return Arrays.asList(this);
	}

	@Override
	public Declaration changeType(Type newType) {
		FunctionDeclaration fD =  new FunctionDeclaration();
		fD.modifiers = this.modifiers;
		fD.name = this.name;
		fD.signature = (Signature)newType; //TODO
		return fD;
	}
	
}
