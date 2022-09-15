package com.luxlunaris.cincia.backend.object;

import java.util.ArrayList;
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

public class Enviro implements Stateful{

	private Enviro parent;
	private Map<String, CinciaObject> vars;
	private Map<String, Type> types;


	public static Enviro getTopLevelEnviro() {
		Enviro e = new Enviro(null);
		e.set("cincia", new Stdlib() );
		return e;
	}

	public Enviro(Enviro parent) {

		this.parent = parent;

		if(parent != null ) {
			this.vars = new HashMap<String, CinciaObject>(parent.vars);
			this.types = new HashMap<String, Type>(parent.types);
		}else {
			this.vars = new HashMap<String, CinciaObject>();
			this.types = new HashMap<String, Type>();
		}

	}

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

	@Override
	public void set(String key, CinciaObject val, Type type) {
		//TODO: maybe add final property in another map to check if reassignment is permitted
		//TODO: add modifiers list in params 

		// variable already exists/declared, need to check type:
		if(vars.containsKey(key)) {

			// if types don't match, error!
			if(!types.get(key).matches(val.getType())) {
				throw new IncompatibleTypesException();
			}

		}

		vars.put(key, val);
		Type oldType = getType(key);
		types.put(key, oldType==null? type : oldType); // if there's an old type, keep it! (for union types to work)
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
