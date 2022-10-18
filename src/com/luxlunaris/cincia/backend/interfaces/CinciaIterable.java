package com.luxlunaris.cincia.backend.interfaces;

import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;

public interface CinciaIterable extends Iterable<CinciaObject>, CinciaObject{
  	
	CinciaIterable map(UnaryOperator<CinciaObject> f);
	CinciaIterable map(CinciaFunction f);
	CinciaIterable filter(Predicate<CinciaObject> f);
	CinciaIterable filter(CinciaFunction f);	
	public CinciaObject reduce(BinaryOperator<CinciaObject> f);
	long size();
}
