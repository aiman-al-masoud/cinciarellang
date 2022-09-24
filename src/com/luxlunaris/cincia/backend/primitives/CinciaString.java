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
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;

//TODO: implement iterable methods
public class CinciaString extends PrimitiveCinciaObject implements CinciaIterable{

	private String value;

	public CinciaString(String value) {
		super(new PrimitiveType(PrimitiveType.STRING));
		this.value = value;
	}
	
	@Override
	void setup() {
		set(IterMethods.filter.toString(), new CinciaMethod(this::filter, this));
	}

	@Override
	public CinciaString __add__(CinciaObject other) {

		try {
			CinciaString otherStr =  (CinciaString)other;
			return new CinciaString(value+otherStr.value);
		} catch (ClassCastException e) {

		}

		throw new RuntimeException("Operator + undefined ...");
	}


	@Override
	public CinciaBool __eq__(CinciaObject other) {

		try {
			CinciaString otherStr = (CinciaString)other;
			return new CinciaBool(otherStr.value.equals(value));
		} catch (Exception e) {

		}

		return new CinciaBool(false);
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
			//			sb.append( get(i.getValue() ).getValue() );
			sb.append( get(i.toJava() ).toJava() );

		}

		return new CinciaString(sb.toString());
	}

	@Override
	public Iterator<CinciaObject> iterator() {
		// TODO Auto-generated method stub
		return Arrays.asList(value.toCharArray()).stream().map(c-> (CinciaObject) new CinciaString(c+"") ).collect(Collectors.toList()).iterator();
//		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long size() {
		return value.length();
	}	

	@Override
	public CinciaIterable map(UnaryOperator<CinciaObject> f) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CinciaObject get(String key) {
		
		if(key.equals(IterMethods.size.toString())) {
			return new CinciaInt((int)size()); //TODO: long to int
		}
		
		return super.get(key);
	}

	@Override
	public CinciaObject reduce(BinaryOperator<CinciaObject> f) {
		// TODO Auto-generated method stub
		return null;
	}


}
