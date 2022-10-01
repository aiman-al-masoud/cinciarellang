package com.luxlunaris.cincia.frontend.ast.interfaces;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;

public interface Declaration extends LeftValue, Ast{
	
	Declaration simplify();
	List<SingleDeclaration> toList();
	
	Declaration changeType(Type newType);
	
}
