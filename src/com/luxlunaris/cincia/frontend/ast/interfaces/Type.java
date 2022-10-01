package com.luxlunaris.cincia.frontend.ast.interfaces;

import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;

public interface Type extends Expression{
	
	public static Type Any = new IdentifierType("Any");
	public static Type Module = new IdentifierType("Module");

	
	boolean matches(Type other);
	
	
	/**
	 * TypeWrapper returns the wrapped type, everything elese
	 * just returns this.
	 * @return
	 */
	Type unwrap();
	
	/**
	 * Resolve eventual custom types within this Signature
	 * 
	 * @param eval
	 * @param enviro
	 * @return
	 */
	Type resolve(Eval eval, Enviro enviro); // TODO: Stateful interface instead of Enviro class
	
	
}
