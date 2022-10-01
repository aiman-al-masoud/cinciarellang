package com.luxlunaris.cincia.backend.stdlib;

import com.luxlunaris.cincia.backend.stdlib.concurrency.Concurrency;
import com.luxlunaris.cincia.backend.stdlib.io.IO;

public class Stdlib extends Module{
	
	public static final String STDLIB = "cincia"; 

	public Stdlib() {
		set("concurrency", new Concurrency());
		set("io", new IO());
	}

}
