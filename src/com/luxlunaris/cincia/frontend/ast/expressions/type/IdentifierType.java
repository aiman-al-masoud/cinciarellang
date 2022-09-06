package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;


public class IdentifierType extends OneNameType{
	
	public String value;
	
	public IdentifierType(String id) {
		this.value = id;
	}
	
	@Override
	public String toString() {
		return value+"";
	}

	
	//TODO: handle Any, and class/interface hierarchies
	@Override
	public boolean matches(Type other) {
		
		try {
			
			return value.equals(((IdentifierType)other).value);
		} catch (ClassCastException e) {
			// TODO: handle exception
		}
		
		return false;
	}

}
