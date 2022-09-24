package com.luxlunaris.cincia.backend.interfaces;

import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;

public interface CinciaIterable extends Iterable<CinciaObject>, CinciaObject{
  	
	CinciaIterable map(UnaryOperator<CinciaObject> f);
	CinciaIterable map(PureCinciaFunction f);
	CinciaIterable filter(Predicate<CinciaObject> f);
	CinciaIterable filter(PureCinciaFunction f);
//	CinciaIterable reduce(PureCinciaFunction f, CinciaObject initial);
	
	public CinciaObject reduce(BinaryOperator<CinciaObject> f);
	
	long size();
}
