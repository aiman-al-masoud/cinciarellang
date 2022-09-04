package com.luxlunaris.cincia.backend;

import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;

public interface CinciaIterable extends Iterable<CinciaObject>, CinciaObject{
	
	CinciaIterable filter(PureCinciaFunction f);
	CinciaIterable map(PureCinciaFunction f);
	CinciaIterable reduce(PureCinciaFunction f, CinciaObject initial);
}
