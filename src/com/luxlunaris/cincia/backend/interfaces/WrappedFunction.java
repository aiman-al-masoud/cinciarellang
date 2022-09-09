package com.luxlunaris.cincia.backend.interfaces;

import java.util.List;


@FunctionalInterface
public interface WrappedFunction{
	CinciaObject run(List<CinciaObject> args);
}
