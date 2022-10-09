package com.luxlunaris.cincia.frontend.ast.interfaces;

import java.util.List;

public interface Expression extends Ast{
	
	Expression simplify();
	List<Expression> toList();
	
}
