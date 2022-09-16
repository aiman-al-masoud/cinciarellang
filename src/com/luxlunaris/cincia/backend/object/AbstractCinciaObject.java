package com.luxlunaris.cincia.backend.object;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaIterable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.iterables.CinciaList;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.backend.throwables.CannotMutateException;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class AbstractCinciaObject implements CinciaObject{

	protected boolean immutable;	
	public Enviro enviro; //object's internal environment 
	protected Type type; // object's type
	CinciaCinciaClass myClass; // object's class

	public AbstractCinciaObject(Type type) {
		this.type = type;
		immutable = false;
		enviro = new Enviro(null); //TODO: parent null?
		set(Magic.THIS, this); 

		if(! (this instanceof CinciaFunction) ) { //else inf recursion upon creating CinciaMethods
			set(Magic.copy, new CinciaMethod(this::copy, this));
			set(Magic.freeze, new CinciaMethod(this::freeze, this));
			set(Magic.as, new CinciaMethod(this::as, this));
			set(Magic.is, new CinciaMethod(this::is, this));
		}

	}

	public CinciaObject get(String key) {
		return enviro.get(key);
	}

	public CinciaObject get(Magic key) {
		return get(key.toString());
	}

	public Type getType(String key) {
		return enviro.getType(key);
	}

	public Type getType() {
		return type;
	}

	@Override
	public Object getValue() {
		return this;
	}

	/**
	 * Throws an exception if this object is immutable.
	 */
	protected void checkImmutable() {

		if(immutable) {	
			throw new CannotMutateException();
		}

	}

	public void set(String key, CinciaObject val, Type type) {

		checkImmutable();
		enviro.set(key, val, type);
	}

	public void set(String key, CinciaObject val) {
		set(key, val, val.getType());
	}

	public void set(Magic key, CinciaObject val) {
		set(key.toString(), val, val.getType());
	}


	public void remove(String key) {

		if(!immutable) {
			enviro.remove(key);
		}else {
			throw new RuntimeException("Cannot mutate immutable object!");
		}
	}

	/**
	 * Recursively makes this object and all of its children immutable.
	 */
	public void setImmutable() {

		immutable = true;

		enviro.values().stream().forEach(o->{

			if(o!=null && o!=this) {
				o.setImmutable();
			}

		});

	}

	public Enviro getEnviro() {
		return enviro;
	}


	public boolean __bool__(){
		//TODO: handle keyerror exception, check if cm null
		CinciaMethod cm = (CinciaMethod)get(Magic.__bool__);
		return (boolean)cm.run(null).getValue();
	}

	@Override
	public CinciaObject __add__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__add__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __sub__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__sub__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __mul__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__mul__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __mod__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__mod__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __div__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__div__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __neg__() {
		CinciaMethod cm = (CinciaMethod)get(Magic.__neg__);
		return cm.run(null);
	}

	@Override
	public CinciaObject __or__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__or__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __and__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__and__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __lt__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__lt__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __gt__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__gt__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __lte__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__lte__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __gte__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__gte__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __eq__(CinciaObject other) {

		try {
			CinciaMethod cm = (CinciaMethod)get(Magic.__eq__);
			return cm.run(Arrays.asList(other));
		} catch (Exception e) {

		}

		return new CinciaBool(this == other); //default is identity comparison
	}

	@Override
	public CinciaObject __ne__(CinciaObject other) {

		try {
			CinciaMethod cm = (CinciaMethod)get(Magic.__ne__);
			return cm.run(Arrays.asList(other));
		} catch (Exception e) {

		}

		return new CinciaBool(!__eq__(other).__bool__());
	}

	@Override
	public CinciaObject __not__() {
		CinciaMethod cm = (CinciaMethod)get(Magic.__not__);
		return cm.run(null);
	}

	@Override
	public CinciaString __str__() {
		CinciaMethod cm = (CinciaMethod)get(Magic.__str__);
		return (CinciaString)cm.run(null);
	}

	@Override
	public CinciaObject __init__(List<CinciaObject> args) {

		try {

			CinciaMethod cm = (CinciaMethod)get(Magic.__init__);
			return cm.run(args);
		} catch (Exception e) {

		}

		return this;
	}

	// .as(ClassName) //TODO: cast/conversion to other class
	@Override
	public CinciaObject as(List<CinciaObject> args) {
		CinciaMethod cm = (CinciaMethod)get(Magic.as);
		return cm.run(args);
	}


	/**
	 * Returns a blank new object of this's kind.
	 */
	protected CinciaObject getBlank() {
		return new AbstractCinciaObject(this.type);
	}

	/**
	 * Returns a recursive copy of this object.
	 */
	@Override
	public CinciaObject copy(List<CinciaObject> args) {

		//TODO: circular references could cause problems

		CinciaObject copy = getBlank(); // get a new (blank) object

		for (Entry<String, CinciaObject> e : enviro.items()) {

			CinciaObject childo = e.getValue(); // child object
			CinciaObject childco; // copy of the child object

			if(childo == this) { // in case child is a self-reference
				childco = copy;
			}else {	// otherwise, copy the child recursively
				childco = childo.copy(args);
			}

			// methods should keep the same code but change their environment to the new object's
			if(childco instanceof CinciaMethod) {

				CinciaMethod methco = (CinciaMethod)childco;

				if(methco.isNativeCode()) { // skip if method is already on object blank copy
					continue;
				}

				methco.parent = copy;
			}

			copy.set(e.getKey(), childco);			
		}

		return copy;
	}

	/**
	 * Returns an immutable recursive copy of this object.
	 */
	@Override
	public CinciaObject freeze(List<CinciaObject> args) {
		CinciaObject o = this.copy(args);
		o.setImmutable();
		return o;
	}

	@Override
	public String toString() {
		return (getValue()==this? super.toString() : getValue())+"";
	}

	@Override
	public CinciaObject get(int key) {
		return get(key+"");
	}

	@Override
	public void set(int key, CinciaObject val, Type type) {		
		set(key+"", val, type);
	}

	@Override
	public void set(int key, CinciaObject val) {
		set(key, val, val.getType());
	}

	@Override
	public Object toJava() {
		return this;
	}

	@Override
	public boolean isImmutable() {
		return immutable;
	}

	@Override
	public void set(CinciaObject key, CinciaObject val) {

		// if index is an int
		if(key instanceof CinciaInt) {
			set(((CinciaInt)key).getValue(), val);
			return;
		}

		// if index is a string
		if(key instanceof CinciaString) {
			set(((CinciaString)key).getValue(), val);
			return;
		}

		// if index is an iterable
		if(key instanceof CinciaIterable) {
			set((CinciaIterable)key, val);
			return;
		}

		throw new RuntimeException("Unsupported index type: "+key.getClass()+"!");
	}

	@Override
	public CinciaObject get(CinciaObject key) {

		if(key instanceof CinciaString) {
			return get(((CinciaString)key).getValue());
		}

		if(key instanceof CinciaInt) {
			return get(((CinciaInt)key).getValue());
		}

		if(key instanceof CinciaIterable) {
			return get((CinciaIterable)key);
		}

		throw new RuntimeException("Unsupported index type: "+key.getClass()+"!");
	}


	@Override
	public void set(CinciaIterable key, CinciaObject val) {

		for(CinciaObject i : key) { //TODO: len(val) may not be == to len(key)
			set(i, val instanceof CinciaIterable ? val.get(i) : val ); // if val is not another list, assign all keys to same single value of val.
		}

	}

	@Override
	public CinciaObject get(CinciaIterable key) {

		List<CinciaObject> l = new ArrayList<>();

		for(CinciaObject i : ((CinciaIterable)key)) {
			l.add(get(i));
		}

		return new CinciaList(l);
	}


	@Override
	public CinciaBool is(List<CinciaObject> args) {
		return new CinciaBool(this == args.get(0));// this == other
	}

	@Override
	public boolean equals(Object obj) {

		try {
			return __eq__((CinciaObject)obj).__bool__();
		} catch (ClassCastException e) {
			throw new RuntimeException("Tried comparing cincia object with non-cincia object");
		}

	}



}
