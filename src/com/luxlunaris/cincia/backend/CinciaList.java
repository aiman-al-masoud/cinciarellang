package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.expressions.type.ListType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaList extends AbstractCinciaObject implements Iterable<CinciaObject> {
	
	protected ArrayList<CinciaObject> list;

	public CinciaList(Type type) {
		super(new ListType(type));
		list = new ArrayList<CinciaObject>();
	}

	@Override
	public Iterator<CinciaObject> iterator() {
		return list.iterator();
	}
	
	@Override
	public CinciaObject get(int key) {
		return list.get(key);
	}
	
	@Override
	public void set(int key, CinciaObject val, Type type) {
		list.set(key, val);
	}
	
	public void add(CinciaObject val) {
		list.add(val);
	}
	
	@Override
	public String toString() {
		return list.toString();
	}
	
	@Override
	public CinciaObject copy(List<CinciaObject> args) {
		CinciaList c = new CinciaList(type);
		c.list = new ArrayList<CinciaObject>(list);
		return c;
	}
	

}
