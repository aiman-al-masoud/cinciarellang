package com.luxlunaris.cincia.backend;


import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;

public class AbstractCinciaObject implements CinciaObject{

	private boolean immutable;	
	public Enviro enviro; //object's internal environment 
	Type type; // object's type
	CinciaClass myClass; // object's class

	public AbstractCinciaObject(Type type) {
		this.type = type;
		immutable = false;
		enviro = new Enviro(null); //TODO: parent null?
		set("this", this, type); //TODO: extract into keywords
		set(Magic.copy, new CinciaMethod(this::copy));
		set(Magic.freeze, new CinciaMethod(this::freeze));
		set(Magic.as, new CinciaMethod(this::as));
	}

	public AbstractCinciaObject get(String key) {
		return enviro.get(key);
	}

	public AbstractCinciaObject get(Magic key) {
		return get(key.toString());
	}

	public Type getType(String key) {
		return enviro.getType(key);
	}

	public void set(String key, AbstractCinciaObject val, Type type) {

		if(!immutable) {
			enviro.set(key, val, type);
		}else {
			throw new RuntimeException("Cannot mutate immutable object!");
		}
	}

	public void set(String key, AbstractCinciaObject val) {
		set(key, val, val.type);
	}

	public void set(Magic key, AbstractCinciaObject val) {
		set(key.toString(), val, val.type);
	}


	public void remove(String key) {

		if(!immutable) {
			enviro.remove(key);
		}else {
			throw new RuntimeException("Cannot mutate immutable object!");
		}
	}

	// recursively make the object immutable.
	public void setImmutable() {

		immutable = true;

		if(enviro.values().size()==0) {//TODO: maybe unnecessary?
			return;
		}

		enviro.values().stream().forEach(o->{
			if(o!=null) {
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

	public AbstractCinciaObject __add__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__add__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __sub__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__sub__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __mul__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__mul__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __mod__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__mod__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __div__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__div__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __neg__() {
		CinciaMethod cm = (CinciaMethod)get(Magic.__neg__);
		return cm.run(null);
	}

	public AbstractCinciaObject __or__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__or__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __and__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__and__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __lt__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__lt__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __gt__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__gt__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __lte__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__lte__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __gte__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__gte__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __eq__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__eq__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __ne__(AbstractCinciaObject other) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__ne__);
		return cm.run(Arrays.asList(other));
	}

	public AbstractCinciaObject __not__() {
		CinciaMethod cm = (CinciaMethod)get(Magic.__not__);
		return cm.run(null);
	}

	public AbstractCinciaObject __str__() {
		CinciaMethod cm = (CinciaMethod)get(Magic.__str__);
		return cm.run(null);
	}

	public AbstractCinciaObject __init__(List<AbstractCinciaObject> args) {
		CinciaMethod cm = (CinciaMethod)get(Magic.__init__);
		return cm.run(args);
	}
	
	// .as(ClassName) //TODO: cast/conversion to other class
	public AbstractCinciaObject as(List<AbstractCinciaObject> args) {
		CinciaMethod cm = (CinciaMethod)get(Magic.as);
		return cm.run(args);
	}

	// return a deep (I believe) copy of this object
	public AbstractCinciaObject copy(List<AbstractCinciaObject> args) {
		AbstractCinciaObject obj = new AbstractCinciaObject(this.type);
		obj.enviro = new Enviro(this.enviro);
		return obj;
	}

	// return an immutable copy of this object
	public AbstractCinciaObject freeze(List<AbstractCinciaObject> args) {
		AbstractCinciaObject o = copy(args);
		o.setImmutable();
		return o;
	}

	@Override
	public Object getValue() {
		return this;
	}

}
