package com.luxlunaris.cincia.backend.object;


import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.throwables.CannotMutateException;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class AbstractCinciaObject implements CinciaObject{

	protected boolean immutable;	
	public Enviro enviro; //object's internal environment 
	protected Type type; // object's type
	CinciaClass myClass; // object's class

	public AbstractCinciaObject(Type type) {
		this.type = type;
		immutable = false;
		enviro = new Enviro(null); //TODO: parent null?

		if(! (this instanceof CinciaFunction) ) {
			set("this", this, type); //TODO: extract into keywords
			set(Magic.copy, new CinciaMethod(this::copy, this));
			set(Magic.freeze, new CinciaMethod(this::freeze, this));
			set(Magic.into, new CinciaMethod(this::into, this));
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
		CinciaMethod cm = (CinciaMethod)get(Magic.__eq__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __ne__(CinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__ne__);
		return cm.run(Arrays.asList(other));
	}

	@Override
	public CinciaObject __not__() {
		CinciaMethod cm = (CinciaMethod)get(Magic.__not__);
		return cm.run(null);
	}

	@Override
	public CinciaObject __str__() {
		CinciaMethod cm = (CinciaMethod)get(Magic.__str__);
		return cm.run(null);
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
	public CinciaObject into(List<CinciaObject> args) {
		CinciaMethod cm = (CinciaMethod)get(Magic.into);
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

		// TEST
		// c = class{ __init__ = \x -> 1; }
		// b = class{ __init__ = \x -> 1; }
		//b.a = c()
		//b.a.r = 1
		//y = b()
		//y.a = c()
		//y.a.r = 2
		//b.a.r // 2 WROOOONG unless attrib is static

		//TEST
		//c = class{ __init__ = \x -> 1; }
		//x.a = 3
		//y = x.copy()
		//y.a = 4
		//x.a //4 WROOONG



		//TODO: circular references could cause problems

		CinciaObject copy = getBlank(); // get a new (blank) object

		enviro.items().forEach(e->{

			CinciaObject o = e.getValue(); // child object
			CinciaObject co; // copy of the object

			if(o == this) { // in case of self references
				co = copy;
			}else {	// otherwise, copy the child recursively
				co = o.copy(args);
			}

			// methods should keep the same code but change their environment to the 
			// new object's
			if(co instanceof CinciaMethod) {
				((CinciaMethod) co).parent = copy;
			}

			copy.set(e.getKey(), co);			
		});


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



}
