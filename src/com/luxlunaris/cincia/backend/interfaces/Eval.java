package com.luxlunaris.cincia.backend.interfaces;

import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;


@FunctionalInterface
public interface Eval{
	CinciaObject eval(Ast ast, Enviro enviro);
}

