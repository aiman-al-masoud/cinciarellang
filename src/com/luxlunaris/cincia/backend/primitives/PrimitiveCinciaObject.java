package com.luxlunaris.cincia.backend.primitives;

import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
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
	
	/**
	 * Primitives are immutable, so you can safely avoid copying.
	 * This is the base case for AbstractCinciaObject's recursive copy.
	 */
	@Override
	public CinciaObject copy(List<CinciaObject> args) {
		return this;
	}
	

}
