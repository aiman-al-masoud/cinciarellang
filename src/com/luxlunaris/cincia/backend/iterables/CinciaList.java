package com.luxlunaris.cincia.backend.iterables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaIterable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.IterMethods;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.ListType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaList extends AbstractCinciaObject implements CinciaIterable {

	protected List<CinciaObject> list;

	public CinciaList(Type type) {
		this(type, new ArrayList<CinciaObject>());
	}
	
	public CinciaList(Type type, List<CinciaObject> list) {
		super(new ListType(type));
		this.list = list;
		set(IterMethods.map.toString(),  new CinciaMethod(this::map, this));
		set(IterMethods.filter.toString(),  new CinciaMethod(this::filter, this));
		set(IterMethods.size.toString(),  new CinciaMethod(this::size, this));

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

		checkImmutable();
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
	
	public CinciaIterable filter(List<CinciaObject> args) {
		return filter((PureCinciaFunction)args.get(0)); 
	}

	@Override
	public CinciaIterable map(PureCinciaFunction f) {
		List<CinciaObject> list = this.list.stream().map( o -> f.run(Arrays.asList(o))).collect(Collectors.toList());
		CinciaList res = new CinciaList(this.type); //TODO: type may not be the same
		res.list = list;
		return res;
	}
	
	public CinciaIterable map(List<CinciaObject> args) {
		return map((PureCinciaFunction)args.get(0)); 
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
	
	public CinciaInt size(List<CinciaObject> args) {
		return new CinciaInt((int)size());
	}

	protected CinciaObject getBlank() {
		return new CinciaList(getType());
	}

	@Override
	public List<Object> toJava() {
		return list.stream().map(o->o.toJava()).collect(Collectors.toList());
	}
	
	public List<CinciaObject> getList(){
		return list;
	}


	@Override
	public CinciaObject __mul__(CinciaObject other) {

		try {
			CinciaInt cincint = (CinciaInt)other;
			List<CinciaObject> li = list.stream().map(x->x.__mul__(cincint)).collect(Collectors.toList());
			CinciaList res = new CinciaList(Type.Any); //specify type
			res.list = li;
			return res;
		} catch (Exception e) {

		}

		throw new RuntimeException("Operation: 'list times "+other.getType()+"' not supported!");
	}

	@Override
	public CinciaObject __eq__(CinciaObject other) {

		try {
			CinciaList otheriter = (CinciaList)other;

			return new CinciaBool( otheriter.list.equals(this.list) );
		} catch (ClassCastException e) {

		}

		return new CinciaBool(false);
	}


}
