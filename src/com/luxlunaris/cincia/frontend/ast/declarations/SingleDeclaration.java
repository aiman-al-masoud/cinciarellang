package com.luxlunaris.cincia.frontend.ast.declarations;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public abstract class SingleDeclaration implements Declaration {
	
	public abstract String getName();
	public abstract Type getType();
	public abstract List<Modifiers> getModifiers();
	public abstract Declaration changeType(Type newType);

}
