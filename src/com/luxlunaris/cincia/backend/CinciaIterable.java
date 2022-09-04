package com.luxlunaris.cincia.backend;


public interface CinciaIterable extends Iterable<CinciaObject>, CinciaObject{
	
	CinciaIterable filter(PureCinciaFunction f);
	CinciaIterable map(PureCinciaFunction f);
	CinciaIterable reduce(PureCinciaFunction f, CinciaObject initial);
	long size();
}
