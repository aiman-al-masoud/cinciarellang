package com.luxlunaris.cincia.backend.interfaces;

import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;

public interface CinciaIterable extends Iterable<CinciaObject>, CinciaObject{
	
	CinciaIterable filter(PureCinciaFunction f);
	CinciaIterable map(PureCinciaFunction f);
	CinciaIterable reduce(PureCinciaFunction f, CinciaObject initial);
	long size();
}
