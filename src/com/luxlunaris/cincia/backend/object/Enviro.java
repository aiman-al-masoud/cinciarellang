package com.luxlunaris.cincia.backend.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.CinciaIterable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.Stateful;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.backend.stdlib.Stdlib;
import com.luxlunaris.cincia.backend.throwables.TypeError;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
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
	protected Map<String, CinciaObject> vars;
	protected Map<String, Type> types;
	protected Map<String, List<Modifiers>> modifiers;


	public static Enviro getTopLevelEnviro() {
		Enviro e = new Enviro(null);
		return e;
	}

	public Enviro(Enviro parent) {

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
	 * Returns a new shallow copy of this env,
	 * ie: DIFFERENT structure, containing the SAME objects.
	 * @return
	 */
	public Enviro newChild() {
		return new Enviro(this);
	}

	@Override
	public CinciaObject get(String key) {

		CinciaObject o = vars.get(key);

		if(o==null && vars.containsKey(key)) {
			throw new RuntimeException(key+" is declared but undefined!");
		}

		if(o==null) {
			throw new RuntimeException(key+" is undefined!");
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
		if(!types.containsKey(key) && val==null) {
			
//			if(key.equals("x")) { //TODO: remove this !!!
//				System.out.println("declaration of x!");
//			}
			
			vars.put(key, null);
			types.put(key, type);
			this.modifiers.put(key, modifiers);
			return;
		}

		// (case 0.1) declaration + assignment
		if(!types.containsKey(key) && val!=null && type!=null) {
			
//			if(key.equals("x")) { //TODO: remove this !!!
//				System.out.println("declaration+assignment of x!");
//			}


			if(!type.matches(val.getType())) {
				//				throw new RuntimeException("Invalid declaration: declared type incompatible with assigned type!");
				TypeError te = new TypeError();
				te.expected = type;
				te.got = val.getType();					
				throw te;
			}

			vars.put(key, val);
			types.put(key, type);
			this.modifiers.put(key, modifiers);
			return;

			//			return;

		}


		// (case 1) assignment with inferred type
		if(!types.containsKey(key) && val!=null ) {
			
//			if(key.equals("x")) { //TODO: remove this !!!
//				System.out.println("assignment w/ inferred type of x!");
//			}

			
			vars.put(key, val);
			types.put(key, val.getType());
			this.modifiers.put(key, modifiers);
			return;
		}




		// (case 2) reassignment/assignment after declaration
		if(types.containsKey(key) && val!=null ) {
			

//			if(key.equals("x")) { //TODO: remove this !!!
//				System.out.println("reassignment/assignment of x! value: "+val);
//			}


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
			if(vars.get(key)!=null && this.modifiers.get(key).contains(Modifiers.FINAL)) {
				throw new RuntimeException("Cannot reassign final variable!");
			}

			// TODO error on new modifiers ...


			//else ...
			vars.put(key, val);
			
//			System.out.println("I did assign x!");
			return;

		}


		// (case 3) redeclaration or setting back to null, always a mistake
		if(types.containsKey(key) && val==null) {
			
//			if(key.equals("x")) { //TODO: remove this !!!
//				System.out.println("redeclaration of x!");
//			}
			
			throw new RuntimeException("Cannot re-declare variable!");
		}


	}

	@Override
	public void set(String key, CinciaObject val, Type type) {
		set(key, val, type, Arrays.asList());
	}

	@Override
	public void set(String key, CinciaObject val) {
		set(key, val, val.getType());
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





}
