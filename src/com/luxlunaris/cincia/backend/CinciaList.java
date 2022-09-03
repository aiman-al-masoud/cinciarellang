package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.Iterator;

import com.luxlunaris.cincia.frontend.ast.expressions.type.ListType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaList extends AbstractCinciaObject implements Iterable<CinciaObject> {
	
	private ArrayList<CinciaObject> list;

	public CinciaList(Type type) {
		super(new ListType(type));
		list = new ArrayList<CinciaObject>();
	}

	@Override
	public Iterator<CinciaObject> iterator() {
		return list.iterator();
	}
	
	@Override
	public CinciaObject get(String key) {
		return list.get(Integer.parseInt(key)); //TODO: fix this stupid solution
	}
	
	@Override
	public void set(String key, CinciaObject val, Type type) {
		list.set(Integer.parseInt(key), val);
	}
		

}
