package com.luxlunaris.cincia.backend.stdlib.io;

import com.luxlunaris.cincia.backend.stdlib.Module;

public class IO extends Module {
	
	public IO() {
		set("print", new Print());
	}

}
