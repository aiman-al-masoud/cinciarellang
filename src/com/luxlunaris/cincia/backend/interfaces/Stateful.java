package com.luxlunaris.cincia.backend.interfaces;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public interface Stateful {
	
	CinciaObject get(String key);
	Type getType(String key);
	void set(String key, CinciaObject val, Type type);
	void set(String key, CinciaObject val);
	void remove(String key);
	
	
}
