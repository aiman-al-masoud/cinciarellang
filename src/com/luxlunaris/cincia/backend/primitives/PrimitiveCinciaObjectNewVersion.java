package com.luxlunaris.cincia.backend.primitives;

import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.CinciaCinciaClass;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class PrimitiveCinciaObjectNewVersion extends CinciaCinciaClass {
	
	protected boolean isInstance;
	
	@Override
	public boolean matches(Type other) {
		return getClass() == other.getClass();
	}
	
	@Override
	public CinciaObject copy(List<CinciaObject> args) {
		return this;
	}
	

}
