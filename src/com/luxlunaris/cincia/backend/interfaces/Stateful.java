package com.luxlunaris.cincia.backend.interfaces;

import java.util.List;

import com.luxlunaris.cincia.backend.object.Magic;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public interface Stateful {
	
	CinciaObject get(String key);
	Type getType(String key);
	void set(String key, CinciaObject val, Type type, List<Modifiers> modifiers);
	void set(String key, CinciaObject val, Type type);
	void set(String key, CinciaObject val);
	void remove(String key);
	CinciaObject get(int key);
	CinciaObject get(Magic key);
	CinciaObject get(CinciaObject key);
	CinciaObject get(CinciaIterable key);
	void set(int key, CinciaObject val, Type type);
	void set(int key, CinciaObject val);
	void set(Magic key, CinciaObject val);
	void set(CinciaObject key, CinciaObject val);
	void set(CinciaIterable key, CinciaObject val);
	
	
//	void set(CinciaObject key, CinciaObject val, Type type, List<Modifiers> modifiers);

	
}
