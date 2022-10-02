package com.luxlunaris.cincia.backend.iterables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaIterable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.IterMethods;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.throwables.TypeError;
import com.luxlunaris.cincia.frontend.ast.expressions.type.ListType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import java.util.function.UnaryOperator;


public class CinciaList extends AbstractCinciaObject implements CinciaIterable {

	protected List<CinciaObject> list;

	public CinciaList(Type type) {
		this(type, new ArrayList<CinciaObject>());
	}

	public CinciaList(List<CinciaObject> list) {
		this(inferType(list), list);
	}

	public CinciaList(Type type, List<CinciaObject> list) {
		super(new ListType(type));
		this.list = list;
		set(IterMethods.map.toString(),  new CinciaMethod(this::map, this));
		set(IterMethods.filter.toString(),  new CinciaMethod(this::filter, this));
		set(IterMethods.reduce.toString(),  new CinciaMethod(this::reduce, this));
		set("add", new CinciaMethod(this::add, this));
	}

	static protected Type inferType(List<CinciaObject> list) {

		if(list.size()==0) {
			return Type.Any;
		}

		boolean singleType = list.stream().allMatch(e->list.get(0).getType().matches(e.getType()));
		return singleType ? list.get(0).getType() : Type.Any;
	}

	@Override
	public Iterator<CinciaObject> iterator() {
		return list.iterator();
	}

	public void add(CinciaObject val) {
		checkImmutable();
		list.add(val);
	}

	public CinciaObject add(List<CinciaObject> args) {		
		args.forEach(a-> add(a));
		return null;
	}

	@Override
	public String toString() {
		return list.toString();
	}

	@Override
	public CinciaObject copy(List<CinciaObject> args) {		
		List<CinciaObject> copy = list.stream().map(e->e.copy(args)).collect(Collectors.toList());
		return new CinciaList(copy);
	}


	@Override
	public CinciaIterable filter(Predicate<CinciaObject> f) {
		List<CinciaObject> list = this.list.stream().filter( f::test ).collect(Collectors.toList());
		CinciaList res = new CinciaList(this.type);
		res.list = list;
		return res;
	}

	@Override
	public CinciaIterable filter(PureCinciaFunction f) {
		return filter(o -> f.run(Arrays.asList(o)).__bool__().toJava());
	}

	public CinciaIterable filter(List<CinciaObject> args) {
		return filter((PureCinciaFunction)args.get(0)); 
	}


	@Override
	public CinciaIterable map(UnaryOperator<CinciaObject> f) {
		List<CinciaObject> list = this.list.stream().map( f::apply  ).collect(Collectors.toList());
		CinciaList res = new CinciaList(this.type); //TODO: type may not be the same
		res.list = list;
		return res;
	}

	@Override
	public CinciaIterable map(PureCinciaFunction f) {
		return map(o -> f.run(Arrays.asList(o)));
	}

	public CinciaIterable map(List<CinciaObject> args) {
		return map((PureCinciaFunction)args.get(0)); 
	}


	@Override
	public CinciaObject reduce(BinaryOperator<CinciaObject> f) {

		Optional<CinciaObject> res = list.stream().reduce(f);

		if(!res.isPresent()) {
			throw new RuntimeException("Error in reducing list!");
		}

		return res.get(); //TODO: maybe make optional wrapper instead?!!!!!
	}


	//	@Override
	public CinciaObject reduce(List<CinciaObject> args) {
		PureCinciaFunction f = (PureCinciaFunction)args.get(0);
		return reduce( (o1, o2)-> f.run(Arrays.asList(o1,o2)) );
	}


	@Override
	public long size() {
		return list.size();
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
	public CinciaIterable __mul__(CinciaObject other) {
		return map( x->x.__mul__(other) );
	}

	@Override
	public CinciaIterable __add__(CinciaObject other) {

		// if other is list, concatenate lists 
		if(other instanceof CinciaList) {
			List<CinciaObject> l1 = new ArrayList<>(getList());
			l1.addAll(((CinciaList)other).getList());
			return new CinciaList(l1);
		}

		// else apply add operation with other for each element of this list
		return map( x->x.__add__(other) );
	}

	@Override
	public CinciaIterable __sub__(CinciaObject other) {
		return map( x->x.__sub__(other) );
	}

	@Override
	public CinciaIterable __div__(CinciaObject other) {
		return map( x->x.__div__(other) );
	}

	@Override
	public CinciaIterable __mod__(CinciaObject other) {
		return map( x->x.__mod__(other) );
	}


	@Override
	public CinciaBool __eq__(CinciaObject other) {

		try {
			CinciaList otheriter = (CinciaList)other;

			return new CinciaBool( otheriter.list.equals(this.list) );
		} catch (ClassCastException e) {

		}

		return new CinciaBool(false);
	}

	@Override
	public CinciaObject get(int key) {
		return list.get(key);
	}

	protected void checkType(Type type) {

		if(!((ListType)this.type).value.matches(type)) {
			TypeError  e = new TypeError();
			e.expected = ((ListType)this.type).value;
			e.got = type;
			throw e;
		}

	}

	@Override
	public void set(int key, CinciaObject val) {
		checkImmutable();
		checkType(val.getType());
		list.set(key, val);
	}


	@Override
	public CinciaObject get(String key) {

		// size attribute 
		if(key.equals(IterMethods.size.toString()) ) {
			return CinciaObject.wrap(size()); //TODO: long to int may cause problems
		}

		return super.get(key);
	}


}
