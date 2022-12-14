package com.luxlunaris.cincia.backend.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.luxlunaris.cincia.backend.interfaces.CinciaIterable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.Stateful;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.backend.throwables.CannotMutateException;
import com.luxlunaris.cincia.backend.throwables.ReassignmentException;
import com.luxlunaris.cincia.backend.throwables.TypeError;
import com.luxlunaris.cincia.backend.throwables.UndefinedError;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public class Enviro implements Stateful{

	/**
	 * Key to set and get the working directory.
	 * 
	 * That is, the directory where import statements will look 
	 * into for relative path imports.
	 */
	public static final String WORKING_DIR = "WORKING_DIR";

	protected Enviro parent;
	protected boolean immutable;
	protected Map<String, CinciaObject> vars;
	protected Map<String, Type> types;
	protected Map<String, List<Modifiers>> modifiers;


	public static Enviro getTopLevelEnviro() {
		Enviro e = new Enviro(null);
		return e;
	}

	public Enviro(Enviro parent) {

		immutable = false;
		this.parent = parent;

		if(parent != null ) {
			this.vars = new HashMap<String, CinciaObject>(parent.vars);
			this.types = new HashMap<String, Type>(parent.types);
			this.modifiers = new HashMap<>(parent.modifiers);
		}else {
			this.vars = new HashMap<String, CinciaObject>();
			this.types = new HashMap<String, Type>();
			this.modifiers = new HashMap<>();
		}

	}
	
	/**
	 * Recursively makes this object and all of its children immutable.
	 */
	public void setImmutable() {

		immutable = true;

		values().forEach(o->{

			if(o!=null && o!=this) {
				o.setImmutable();
			}

		});

	}
	
	/**
	 * Throws an exception if this object is immutable.
	 */
	protected void checkImmutable() {

		if(immutable) {	
			throw new CannotMutateException();
		}

	}
	
//	@Override
	public boolean isImmutable() {
		return immutable;
	}


	/**
	 * Returns a new shallow copy of this env,
	 * ie: DIFFERENT structure, containing the SAME objects.
	 * @return
	 */
	public Enviro shallowCopy() {
		return new Enviro(this);
	}

	@Override
	public CinciaObject get(String key) {

		CinciaObject o = vars.get(key);

		if(o==null && vars.containsKey(key)) {
			throw new RuntimeException(key+" is declared but undefined!");
		}

		if(o==null) {
			throw new UndefinedError(key);
		}

		return o;

	}
	
	@Override
	public Type getType(String key) {
		return types.get(key);
	}

	public List<Modifiers> getModifiers(String key){
		return modifiers.get(key);
	}

	@Override
	public void set(String key, CinciaObject val, Type type, List<Modifiers> modifiers) {
		
		checkImmutable();


		// (case 0) if key doesn't exist and val is null, it's a declaration
		// set the variable's type to type, and value to null (and modifiers if any)

		// (case 1) if key doesn't exist and val is not null, it's an assignment with inferred type
		// set the variable's type, value and modifiers if any.

		// (case 2) if key does exist, it's an assignment/reassignment
		// get expected type
		// compare expected type with given type (error out if not a match)
		// if variable is final and already non-null, error out.
		// else set value, preserving old type

		// (case 3) redeclaration or setting back to null, always a mistake
		// throw an error


		// (case 0) declaration
		if(!types.containsKey(key) && val==null  && type!=null) {
			vars.put(key, null);
			types.put(key, type);
			this.modifiers.put(key, modifiers);
			return;
		}

		// (case 0.1) declaration + assignment
		if(!types.containsKey(key) && val!=null && type!=null) {
			

			if(!type.matches(val.getType())) {
//				System.out.println(key+" "+val);
//				System.out.println(val.getType().getClass());
//				System.out.println(type.getClass());
			
				TypeError te = new TypeError("Invalid declaration: declared type incompatible with assigned type!");
				te.expected = type;
				te.got = val.getType();					
				throw te;
			}

			vars.put(key, val);
			types.put(key, type);
			this.modifiers.put(key, modifiers);
			return;
		}


		// (case 1) assignment with inferred type
		if(!types.containsKey(key) && val!=null ) {

			vars.put(key, val);
			types.put(key, val.getType());
			this.modifiers.put(key, modifiers);
			return;
		}

		// (case 2) reassignment/assignment after declaration
		if(types.containsKey(key) && val!=null ) {
			
//			System.out.println("here! "+key+" "+val);

			var expectedType = types.get(key);
			var givenType = val.getType();

			// error: assignment with wrong type
			if(!expectedType.matches(givenType)) {
				TypeError te = new TypeError();
				te.expected = expectedType;
				te.got = givenType;					
				throw te;
			}

			// error: reassignment of final variable
			if(vars.get(key)!=null &&  this.modifiers.get(key)!=null &&  this.modifiers.get(key).contains(Modifiers.FINAL)) {
				throw new ReassignmentException(key);
			}

			// TODO error on new modifiers ...


			//else ...
			vars.put(key, val);
			return;

		}


		// (case 3) redeclaration or setting back to null, always a mistake
		if(types.containsKey(key) && val==null) {
			throw new RuntimeException("Cannot re-declare variable!");
		}


		// (case 4) declaration of null variable with no type  ??\_(???)_/?? 
		if(!types.containsKey(key) && val==null && type==null) {
			throw new RuntimeException("Really? What are you trying to do? ??\\_(???)_/?? "+key+" has type and val == null");
		}


	}

	@Override
	public void set(String key, CinciaObject val, Type type) {
		set(key, val, type, Arrays.asList());
	}

	@Override
	public void set(String key, CinciaObject val) {//TODO!!
//		set(key, val, val.getType()); //TODO //TODO!!!
		set(key, val, val ==null ? Type.Any: val.getType());//TODO::/!!!!
	}

	@Override
	public void remove(String key) {
		vars.remove(key);
		types.remove(key);
	}

	public List<CinciaObject> values(){
		return new ArrayList<CinciaObject>(vars.values());
	}

	public List<Map.Entry<String, CinciaObject>> items(){
		return new ArrayList<Map.Entry<String,CinciaObject>>(vars.entrySet());
	}

	@Override
	public CinciaObject get(int key) {
		return get(key+"");
	}

	@Override
	public CinciaObject get(Magic key) {
		return get(key.toString());
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

	@Override
	public CinciaObject get(CinciaIterable key) {
		return key.map( k->get(k) );
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
	public void set(Magic key, CinciaObject val) {
		set(key.toString(), val, val.getType());
	}

	@Override
	public void set(CinciaObject key, CinciaObject val) {

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

		for(CinciaObject i : key) { //TODO: len(val) may not be == to len(key)
			set(i, val instanceof CinciaIterable ? val.get(i) : val ); // if val is not another list, assign all keys to same single value of val.
		}

	}
	
	/**
	 * Return a new enviro that is a shallow copy of this AND the other
	 * @param enviro
	 * @return
	 */
	public Enviro mergeWith(Enviro other) {
		var copy = other.shallowCopy();
		vars.entrySet().forEach(e->copy.set(e.getKey(), e.getValue()));
		return copy;
	}





}
