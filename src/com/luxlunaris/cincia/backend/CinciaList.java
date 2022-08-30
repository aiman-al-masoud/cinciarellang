package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.Iterator;

import com.luxlunaris.cincia.frontend.ast.expressions.type.ListType;


public class CinciaList extends CinciaObject implements Iterable<CinciaObject>{
	
	ArrayList<CinciaObject> list;
	
	public CinciaList() {
		super(new ListType());
		list = new ArrayList<CinciaObject>();
	}
	
	public void add(CinciaObject obj) {
		list.add(obj);
	}
	
	public CinciaObject get(int index) {
		return list.get(index);
	}
	
	public void remove(int index) {
		list.remove(index);
	}
	
	@Override
	public Iterator<CinciaObject> iterator() {
		return list.iterator();
	}
	
}
