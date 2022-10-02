package com.luxlunaris.cincia.backend.object;


import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaClass;
import com.luxlunaris.cincia.backend.interfaces.CinciaIterable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.iterables.CinciaList;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.backend.throwables.CannotMutateException;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public class AbstractCinciaObject implements CinciaObject{

	protected boolean immutable;	
	public Enviro enviro; //object's internal environment 
	protected Type type; // object's type/class
	protected String docString;

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
			set(Magic.help, new CinciaMethod(this::help, this));
			set("values", new CinciaMethod( this::values  , this)); //TODO: extract name
			set("entries", new CinciaMethod(this::entries, this));
			//TODO: add entries() 
		}

		// TODO: fiiiiiiiiiiiiiiiix! and make this prop final
		if(type instanceof CinciaObject) {
			set("type", (CinciaObject)type); //TODO: extract name			
		}

	}

	protected CinciaObject values(List<CinciaObject> args){
		return new CinciaList(enviro.values());
	}

	protected CinciaObject entries(List<CinciaObject> args){
		
		List<CinciaObject> list =
		enviro.vars.entrySet()
			  .stream()
			  .map(e-> new CinciaList( Arrays.asList(CinciaObject.wrap(e.getKey()), e.getValue() ) )  )
			  .collect(Collectors.toList());
		
		return new CinciaList(list);
	}


	@Override
	public CinciaString help(List<CinciaObject> args) {

		String aboutClass = "";

		// TODO: turn int, float etc ... (primitive types) into CinciaClasses
		try {
			CinciaClass myClass = (CinciaClass)type;  
			aboutClass = myClass!=null? myClass.help(args).toJava() : "";
		} catch (ClassCastException e) {

		}

		return new CinciaString("About this object:\n"+docString+"\nAbout the class:\n"+aboutClass);
	}

	@Override
	public void setDocstring(String docString) {
		this.docString  = this.docString==null ? docString : this.docString;
	}


	public Type getType() {
		return type;
	}


	/**
	 * Throws an exception if this object is immutable.
	 */
	protected void checkImmutable() {

		if(immutable) {	
			throw new CannotMutateException();
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


	public CinciaBool __bool__(){
		//TODO: handle keyerror exception, check if cm null
		CinciaMethod cm = (CinciaMethod)get(Magic.__bool__);
		return (CinciaBool)cm.run(null);
	}

	//TODO: commutative operations should try the inverse if one way fails

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
	public CinciaBool __eq__(CinciaObject other) {

		try {
			CinciaMethod cm = (CinciaMethod)get(Magic.__eq__);
			return  (CinciaBool) cm.run(Arrays.asList(other));
		} catch (Exception e) {

		}

		return new CinciaBool(this == other); //default is identity comparison
	}

	@Override
	public CinciaBool __ne__(CinciaObject other) {
		return __eq__(other).__not__(); // always negation of __eq__
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
		
		// TODO this is really just a void method
		
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
				childco = copy; //childco is the copied object's this, ie the copy itself

			}else if (childo == type && childo instanceof CinciaClass) { // if childco is a type 
				childco = (CinciaClass)type; // type reference needs to point to the same type!
				
			}else if (childo == null) { //TODO?
				childco = null;
				
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
		return (toJava()==this? super.toString() : toJava())+"";
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
	public CinciaBool is(List<CinciaObject> args) {
		return new CinciaBool(this == args.get(0)); // this == other (in RAM)
	}

	@Override
	public boolean equals(Object obj) {

		try {
			return __eq__((CinciaObject)obj).__bool__().toJava();
		} catch (ClassCastException e) {
			//			throw new RuntimeException("Tried comparing cincia object with non-cincia object");
			return false;
		}

	}

	//getters

	@Override
	public CinciaObject get(int key) {
		return enviro.get(key);
	}

	@Override
	public CinciaObject get(CinciaIterable key) {		
		//		return enviro.get(key);
		return key.map( k->get(k) );
	}	

	@Override
	public CinciaObject get(CinciaObject key) {

		if(key instanceof CinciaString) {
			return get(((CinciaString)key).toJava());
		}

		if(key instanceof CinciaInt) {
			return get(((CinciaInt)key).toJava());
		}

		if(key instanceof CinciaIterable) {
			return get((CinciaIterable)key);
		}

		throw new RuntimeException("Unsupported index type: "+key.getClass()+"!");
	}

	public CinciaObject get(String key) {
		return enviro.get(key);
	}

	public CinciaObject get(Magic key) {
		return enviro.get(key);
	}

	public Type getType(String key) {
		return enviro.getType(key);
	}

	// setters

	@Override
	public void set(int key, CinciaObject val, Type type) {	
		checkImmutable();
		enviro.set(key, val, type);
	}

	@Override
	public void set(int key, CinciaObject val) {
		checkImmutable();
		enviro.set(key, val);
	}

	@Override
	public void set(CinciaObject key, CinciaObject val) {
		checkImmutable();

		// if index is an int
		if(key instanceof CinciaInt) {
			set(((CinciaInt)key).toJava(), val);
			return;
		}

		// if index is a string
		if(key instanceof CinciaString) {
			set(((CinciaString)key).toJava(), val);
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
	public void set(CinciaIterable key, CinciaObject val) {

		checkImmutable();
		// if val is not another list, assign all keys to same single value of val.
		key.forEach(i -> set(i, val instanceof CinciaIterable ? val.get(i) : val ));
	}

	@Override
	public void set(String key, CinciaObject val, Type type, List<Modifiers> modifiers) {
		
//		System.out.println(key+" "+val+" "+type+" "+modifiers);
		
		checkImmutable();
		enviro.set(key, val, type, modifiers);
	}

	public void set(String key, CinciaObject val) {
		checkImmutable();
		enviro.set(key, val, val ==null ? Type.Any: val.getType());//TODO::/!!!!
	}
	
	
	public void set(String key, CinciaObject val, Type type) {
		checkImmutable();
		enviro.set(key, val, type);
	}


	public void set(Magic key, CinciaObject val) {
		checkImmutable();
		enviro.set(key, val);
	}

	public void remove(String key) {
		checkImmutable();
		enviro.remove(key);
	}

	@Override
	public Object toJava(Object... args) {
		return toJava();
	}


}
