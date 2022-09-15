package com.luxlunaris.cincia.backend.stdlib.concurrency;

import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;

public class Concurrency extends AbstractCinciaObject{

	public Concurrency() {
		super(new IdentifierType("Module"));
		set("Promise", new Promise());
	}

}