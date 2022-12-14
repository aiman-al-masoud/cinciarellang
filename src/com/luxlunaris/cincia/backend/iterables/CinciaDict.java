package com.luxlunaris.cincia.backend.iterables;

import java.util.Optional;

import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.BaseCinciaObject;
import com.luxlunaris.cincia.frontend.ast.expressions.type.DictType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaDict extends BaseCinciaObject {

	public CinciaDict(Type keyType, Type valType) {
		super(new DictType(keyType, valType));
	}
	
	
	@Override
	public String toString() {
		
		Optional<String> d =  items().stream()
											.filter(e->e.getValue()!=this)
											.filter(e-> ! (e.getValue() instanceof CinciaMethod))
				 	                        .map(e->e.getKey()+": "+e.getValue())
				                        	.reduce((e1,e2)->e1+", "+e2);
	
		return "{"+(d.isPresent()?d.get():"")+"}";
	}
	
	
	@Override
	protected CinciaObject getBlank() {
		return new CinciaDict(((DictType)getType()).keyType,((DictType)getType()).valType);
	}

}
