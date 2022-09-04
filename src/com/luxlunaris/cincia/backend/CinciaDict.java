package com.luxlunaris.cincia.backend;

import java.util.Optional;

import com.luxlunaris.cincia.frontend.ast.expressions.type.DictType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaDict extends AbstractCinciaObject {

	public CinciaDict(Type keyType, Type valType) {
		super(new DictType(keyType, valType));
	}
	
	
	@Override
	public String toString() {
		
		Optional<String> d =  enviro.items().stream()
											.filter(e->e.getValue()!=this)
											.filter(e-> ! (e.getValue() instanceof CinciaMethod))
				 	                        .map(e->e.getKey()+": "+e.getValue())
				                        	.reduce((e1,e2)->e1+", "+e2);
		return "{"+d.get()+"}";
	}

}
