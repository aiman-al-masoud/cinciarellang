package com.luxlunaris.cincia.backend.stdlib.concurrency;

import com.luxlunaris.cincia.backend.stdlib.Module;

public class Concurrency extends Module{

	public Concurrency() {
		set("Promise", new Promise());
		set("setTimeout", new SetTimeout());
		set("sleep", new Sleep());
	}

}