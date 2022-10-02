package com.luxlunaris.cincia.backend.primitives;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaIterable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.IterMethods;
import com.luxlunaris.cincia.backend.iterables.CinciaList;
import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

//TODO: implement iterable methods
public class CinciaString extends PrimitiveCinciaObject implements CinciaIterable{

	private String value;

	public CinciaString(String value) {
		super(new TypeWrapper(new PrimitiveType(PrimitiveType.STRING)));
		this.value = value;
	}

	@Override
	void setup() {
		set(IterMethods.filter.toString(), new CinciaMethod(this::filter, this));
		set(IterMethods.map.toString(), new CinciaMethod(this::map, this));
		set(IterMethods.reduce.toString(), new CinciaMethod(this::reduce, this));
	}

	@Override
	public CinciaString __add__(CinciaObject other) {
		return new CinciaString(this.value+other.__str__().toJava());
	}

	@Override
	public CinciaBool __eq__(CinciaObject other) {
		return  (CinciaBool) CinciaObject.wrap( value.equals(other.toJava()));
	}

	@Override
	public String toString() {
		return "\""+value+"\""; //TODO: remove "s when using print
	}

	@Override
	public String toJava() {
		return value;
	}

	@Override
	public CinciaString get(int key) {
		return new CinciaString(value.charAt(key)+"");
	}

	@Override
	public CinciaString get(CinciaIterable key) {

		StringBuilder sb = new StringBuilder();

		for ( CinciaObject o :  key ) {
			CinciaInt i  = (CinciaInt)o;
			sb.append( get(i.toJava() ).toJava() );
		}

		return new CinciaString(sb.toString());
	}

	@Override
	public Iterator<CinciaObject> iterator() {
		return Arrays.asList(value.toCharArray()).stream().map(c-> (CinciaObject) new CinciaString(c+"") ).collect(Collectors.toList()).iterator();
	}

	@Override
	public CinciaIterable filter(PureCinciaFunction f) {
		return filter(o -> f.run(Arrays.asList(o)).__bool__().toJava());
	}

	@Override
	public CinciaIterable filter(Predicate<CinciaObject> f) {

		Optional<CinciaString> filtered = this.value.chars()
				.mapToObj( c-> new CinciaString(((char)c)+"")  )
				.filter( f::test )
				.reduce( ( c1,c2 )-> c1.__add__(c2));

		return filtered.orElse(this);// TODO: return copy instead?

	}

	public CinciaIterable filter(List<CinciaObject> args) {
		return filter((PureCinciaFunction)args.get(0)); 
	}

	@Override
	public CinciaIterable map(PureCinciaFunction f) {
		return map(o -> f.run(Arrays.asList(o)));
	}

	public CinciaIterable map(List<CinciaObject> args) {
		return map((PureCinciaFunction)args.get(0)); 
	}

	@Override
	public long size() {
		return value.length();
	}	

	@Override
	public CinciaIterable map(UnaryOperator<CinciaObject> f) {

		List<CinciaObject> l =  this.value.chars()
				.mapToObj(c-> f.apply( new CinciaString(((char)c)+"") ))
				.collect(Collectors.toList());       

		return new CinciaList(l);
	}

	@Override
	public CinciaObject get(String key) {

		if(key.equals(IterMethods.size.toString())) {
			return CinciaObject.wrap(size()); //TODO: long to int
		}

		return super.get(key);
	}

	@Override
	public CinciaObject reduce(BinaryOperator<CinciaObject> f) {

		Optional<CinciaObject> res = this.value.chars().mapToObj(c-> (CinciaObject)  new CinciaString(((char)c)+"")).reduce(f);

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
	public CinciaString __str__() {
		return this;
	}


}
