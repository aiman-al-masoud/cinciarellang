package com.luxlunaris.cincia.backend.stdlib;

import com.luxlunaris.cincia.backend.stdlib.concurrency.Concurrency;

public class Stdlib extends Module{

	public Stdlib() {
		set("concurrency", new Concurrency());
	}

}
