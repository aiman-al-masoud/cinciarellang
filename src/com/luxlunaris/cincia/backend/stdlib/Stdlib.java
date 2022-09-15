package com.luxlunaris.cincia.backend.stdlib;

import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.backend.stdlib.concurrency.Concurrency;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;

public class Stdlib extends AbstractCinciaObject{

	public Stdlib() {
		super(new IdentifierType("Module"));
		set("concurrency", new Concurrency());
	}

}
