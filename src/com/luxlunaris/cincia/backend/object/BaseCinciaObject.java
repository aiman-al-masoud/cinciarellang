package com.luxlunaris.cincia.backend.object;


import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaClass;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.iterables.CinciaList;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class BaseCinciaObject extends Enviro implements CinciaObject{

	protected Type type; // this object's type/class
	protected String docString;

	public BaseCinciaObject(Type type) {
		super(null); //TODO: parent null?
		this.type = type;
		set(Magic.THIS, this); 

		if(! (this instanceof CinciaFunction) ) { //else inf recursion upon creating CinciaMethods
			set(Magic.copy, new CinciaMethod(this::copy, this));
			set(Magic.freeze, new CinciaMethod(this::freeze, this));
			set(Magic.as, new CinciaMethod(this::as, this));
			set(Magic.is, new CinciaMethod(this::is, this));
			set(Magic.help, new CinciaMethod(this::help, this));
			set(Magic.entries, new CinciaMethod(this::entries, this));
		}

		// TODO: fiiiiiiiiiiiiiiiix! and make this prop final
		if(type instanceof CinciaObject) {
			set(Magic.type, (CinciaObject)type); //TODO: extract name			
		}

	}

	protected CinciaObject entries(List<CinciaObject> args){

		List<CinciaObject> list =
				this.vars.entrySet()
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

	public Enviro getEnviro() {
		return this;
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

		return this; // default constructor is valid
	}

	@Override
	public CinciaObject as(List<CinciaObject> args) {
		CinciaMethod cm = (CinciaMethod)get(Magic.as);
		return cm.run(args);
	}

	/**
	 * Returns a blank new object of this's kind.
	 */
	protected CinciaObject getBlank() {
		return new BaseCinciaObject(this.type);
	}

	/**
	 * Returns a recursive deep copy of the object.
	 */
	@Override
	public CinciaObject copy(List<CinciaObject> args) { //TODO: circular references could cause problems

		CinciaObject copy = getBlank(); // get a new (blank) object

		for (Entry<String, CinciaObject> e : this.items()) { // for each item in (this) original object

			// avoid re-copying item if key is already taken, avoid copying native-code methods
			if( copy.getEnviro().vars.containsKey(e.getKey()) ) {
				continue;
			}

			CinciaObject childo = e.getValue(); // child object
			CinciaObject childco; // copy of the child object

			if(childo == this) { // in case child is a self-reference
				childco = copy; //childco is the copied object's "this", ie: the copy itself

			}else if (childo == type && childo instanceof CinciaClass) { // if childco is a type 
				childco = (CinciaClass)type; // type reference needs to point to the same type!

			}else if (childo == null) { // if child is undefined (declared but undefined)
				childco = null;

			}else {	// otherwise, copy the child recursively, passing it a copy of its parent
				childco = childo.copy(Arrays.asList(copy));
			}

			copy.set(e.getKey(), childco);			
		}

		return copy;
	}

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
	public final CinciaBool is(List<CinciaObject> args) {
		return new CinciaBool(this == args.get(0)); // this == other (in RAM)
	}

	@Override
	public boolean equals(Object obj) {

		try {
			return __eq__((CinciaObject)obj).__bool__().toJava();
		} catch (ClassCastException e) {
			return false;
		}

	}

	@Override
	public Object toJava(Object... args) {
		return toJava();
	}

	@Override
	public Object toJava() {
		return this;
	}

}
