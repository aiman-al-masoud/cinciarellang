package com.luxlunaris.cincia.backend.types;

import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class TypeWrapper extends AbstractCinciaObject implements Type {

	public TypeWrapper(Type type) {
		super(type);
	}

	@Override
	public Expression simplify() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean matches(Type other) {
		
//		System.out.println("in type wrapper");
//		System.out.println("my type: "+type.getClass());
//		System.out.println("other type: "+other.getClass());

		
		if(other instanceof TypeWrapper) {
			other = ((TypeWrapper) other).type;
		}
		
		
		return type.matches(other);
	}
	
	@Override
	public String toString() {
		return type+"";
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	@Override
	public Type unwrap() {
		return type;
	}

}
