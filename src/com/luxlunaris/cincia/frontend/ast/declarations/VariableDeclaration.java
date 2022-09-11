package com.luxlunaris.cincia.frontend.ast.declarations;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.LeftValue;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

/**
 * 
 * Name, type, and modifers of a (possibly uninitialized) variable.
 * modifiers can be empty, type can be null if inferred.
 *
 */
public class VariableDeclaration extends SingleDeclaration{
	
	public List<Modifiers> modifiers;
	public Identifier name;
	public Type type; 
	
	public VariableDeclaration() {
		modifiers = new ArrayList<Modifiers>();
		type = Type.Any; //type is Any by default
	}
	
	public void addModifier(Modifier modifier) {
		modifiers.add(modifier.value);
	}
	
	@Override
		public String toString() {
			return "("+modifiers+" "+name+":"+type+")";
		}

	@Override
	public Declaration simplify() {
		this.type = (Type) type.simplify();
		return this;
	}

	@Override
	public String getName() {
		return name.value;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public List<Modifiers> getModifiers() {
		return modifiers;
	}
	
}
