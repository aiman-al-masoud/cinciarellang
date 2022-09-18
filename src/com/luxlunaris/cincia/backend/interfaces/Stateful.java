package com.luxlunaris.cincia.backend.interfaces;

import java.util.List;

import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public interface Stateful {
	
	CinciaObject get(String key);
	Type getType(String key);
	void set(String key, CinciaObject val, Type type);
	void set(String key, CinciaObject val);
	void remove(String key);
	
	public void set(String key, CinciaObject val, Type type, List<Modifiers> modifiers);
	
}
