package com.luxlunaris.cincia.backend.callables;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public class Parameter {
	
	public String name;
	public Type type;
	public List<Modifiers> modifiers;
	
	
	public Parameter(String name, Type type, List<Modifiers> modifiers) {
		this.name = name;
		this.type = type;
		this.modifiers = modifiers;
	}
	
	/**
	 * Should this parameter be passed by reference?
	 * @return
	 */
	public boolean isByRef() {
		return modifiers.contains(Modifiers.REF);
	}

}
