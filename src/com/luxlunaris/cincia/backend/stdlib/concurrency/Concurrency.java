package com.luxlunaris.cincia.backend.stdlib.concurrency;

import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.backend.stdlib.Module;

public class Concurrency extends Module{

	public Concurrency() {
		set("Promise", new Promise());
		set("setTimeout", new SetTimeout());
		set("sleep", new Sleep());
	}
	
	@Override
	public CinciaString help(List<CinciaObject> args) {
		return new CinciaString("The concurrency module contains some tools that aid in developing simple multi-threaded applications.") ;
	}

}