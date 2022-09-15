package com.luxlunaris.cincia.backend.stdlib;

import com.luxlunaris.cincia.backend.stdlib.concurrency.Concurrency;

public class Stdlib extends Module{
	
	public static final String STDLIB = "cincia"; 

	public Stdlib() {
		set("concurrency", new Concurrency());
	}

}
