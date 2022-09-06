package com.luxlunaris.cincia.frontend.ast.expressions.type;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

//TODO: add in EBNF
public class DictType extends CollectionType{
	
	public Type keyType;
	public Type valType;
	
	public DictType(Type keyType, Type valType) {
		this.keyType  =keyType;
		this.valType  =valType;
	}
	
	public DictType() {
		
	}
	
	@Override
	public String toString() {
		return "{"+keyType+" : "+valType+"}";
	}

	@Override
	public boolean matches(Type other) {
		
		try {
			
			DictType otherdt = (DictType)other;
			return otherdt.keyType.matches(keyType) && otherdt.valType.matches(valType);
			
		}catch (ClassCastException e) {
			
		}
		
		return false;
	}
	
}
