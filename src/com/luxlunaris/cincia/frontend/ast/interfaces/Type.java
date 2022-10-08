package com.luxlunaris.cincia.frontend.ast.interfaces;

import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

public interface Type extends Expression{

	public static Type Any  = new PrimitiveType(PrimitiveType.ANY);
	public static Type Module  = new PrimitiveType(PrimitiveType.MODULE);
	
	/**
	 * True if this type matches the other type. It's not commutative, so it 
	 * may not be true the other way around.
	 * @param other
	 * @return
	 */
	boolean matches(Type other);


	/**
	 * TypeWrapper returns the wrapped type, everything elese
	 * just returns this.
	 * @return
	 */
	Type unwrap();

	/**
	 * Resolve eventual nested user-defined Types within this Type.
	 * 
	 * @param eval
	 * @param enviro
	 * @return
	 */
	Type resolve(Eval eval, Enviro enviro); // TODO: Stateful interface instead of Enviro class


}
