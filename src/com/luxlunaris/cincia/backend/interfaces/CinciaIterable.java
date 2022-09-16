package com.luxlunaris.cincia.backend.interfaces;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;

public interface CinciaIterable extends Iterable<CinciaObject>, CinciaObject{
  	
	public CinciaIterable filter(Predicate<CinciaObject> f);
	public CinciaIterable map(UnaryOperator<CinciaObject> f);
	
	
	CinciaIterable filter(PureCinciaFunction f);
	CinciaIterable map(PureCinciaFunction f);
	CinciaIterable reduce(PureCinciaFunction f, CinciaObject initial);
	long size();
}
