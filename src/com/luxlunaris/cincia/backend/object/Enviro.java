package com.luxlunaris.cincia.backend.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.Stateful;
import com.luxlunaris.cincia.backend.stdlib.Stdlib;
import com.luxlunaris.cincia.backend.throwables.IncompatibleTypesException;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifier;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public class Enviro implements Stateful{

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


		// variable already exists/declared, need to check type:
		if(vars.containsKey(key)) {

			// if types don't match, error!
			if(!types.get(key).matches(val.getType())) {
				throw new IncompatibleTypesException();
			}

//			// if variable is already defined and it is final, throw error!
			if( vars.get(key)!=null && getModifiers(key).contains(Modifiers.FINAL)) {
				throw new RuntimeException("Cannot reassign final reference!");
			}

		}

		vars.put(key, val);
		Type oldType = getType(key);
		List<Modifiers> oldModifiers = getModifiers(key);

		types.put(key, oldType==null? type : oldType); // if there's an old type, keep it! (for union types to work)
		this.modifiers.put(key,   oldModifiers==null? modifiers : oldModifiers); // same for modifiers
	}

	@Override
	public void set(String key, CinciaObject val, Type type) {

		set(key, val, type, Arrays.asList());

		//		//TODO: maybe add final property in another map to check if reassignment is permitted
		//		//TODO: add modifiers list in params 
		//
		//		// variable already exists/declared, need to check type:
		//		if(vars.containsKey(key)) {
		//
		//			// if types don't match, error!
		//			if(!types.get(key).matches(val.getType())) {
		//				throw new IncompatibleTypesException();
		//			}
		//
		//		}
		//
		//		vars.put(key, val);
		//		Type oldType = getType(key);
		//		types.put(key, oldType==null? type : oldType); // if there's an old type, keep it! (for union types to work)
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


}
