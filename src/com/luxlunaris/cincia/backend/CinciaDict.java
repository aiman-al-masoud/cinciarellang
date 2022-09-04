package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.expressions.type.DictType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaDict extends AbstractCinciaObject {

	public CinciaDict(Type keyType, Type valType) {
		super(new DictType(keyType, valType));
	}

}
