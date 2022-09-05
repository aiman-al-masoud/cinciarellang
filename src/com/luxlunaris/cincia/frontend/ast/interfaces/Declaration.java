package com.luxlunaris.cincia.frontend.ast.interfaces;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public interface Declaration extends LeftValue, Ast{
	
	Declaration simplify();
	
}
