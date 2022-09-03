package com.luxlunaris.cincia.backend;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

/**
 * Primitive objects are immutable (int, float, bool, string).
 *
 */
public class PrimitiveCinciaObject extends AbstractCinciaObject{

	public PrimitiveCinciaObject(Type type) {
		super(type);
		setImmutable();
	}
	
	@Override
	public CinciaObject copy(List<CinciaObject> args) {
		return this;
	}
	

}
