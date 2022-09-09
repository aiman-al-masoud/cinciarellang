package com.luxlunaris.cincia.backend.iterables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaIterable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.frontend.ast.expressions.type.ListType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaList extends AbstractCinciaObject implements CinciaIterable {
	
	protected List<CinciaObject> list;

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

	@Override
	public CinciaIterable filter(PureCinciaFunction f) {
		
		List<CinciaObject> list = this.list.stream().filter( o -> f.run(Arrays.asList(o)).__bool__() ).collect(Collectors.toList());
		CinciaList res = new CinciaList(this.type);
		res.list = list;
		return res;
		
	}

	@Override
	public CinciaIterable map(PureCinciaFunction f) {
		
		List<CinciaObject> list = this.list.stream().map( o -> f.run(Arrays.asList(o))).collect(Collectors.toList());
		CinciaList res = new CinciaList(this.type); //TODO: type may not be the same
		res.list = list;
		return res;
	}

	@Override
	public CinciaIterable reduce(PureCinciaFunction f, CinciaObject initial) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long size() {
		return list.size();
	}
	

}
