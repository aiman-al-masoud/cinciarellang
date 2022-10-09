package com.luxlunaris.cincia.backend.interfaces;

import java.util.List;

import com.luxlunaris.cincia.backend.object.Enviro;

public interface Callable {
	
	CinciaObject run(List<CinciaObject> args, Enviro enviro);
	

}
