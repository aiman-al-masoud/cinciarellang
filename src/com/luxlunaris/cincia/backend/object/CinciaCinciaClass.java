package com.luxlunaris.cincia.backend.object;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaClass;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.throwables.TypeError;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaCinciaClass extends BaseCinciaObject implements CinciaClass{


	public CinciaCinciaClass() {
		super(new IdentifierType("Class"));
		type = this; // a class IS its own type
	}

	/**
	 * Creates and returns a new object of the same type as this class.
	 * @param args
	 * @return
	 */
	@Override
	public CinciaObject newInstance(List<CinciaObject> args) {

		//Check for declared but undefined methods on this class before creating a copy (instance/object).
//		boolean undefinedMethods = this.getEnviro().vars.entrySet().stream().anyMatch( e-> e.getValue()==null && (this.getEnviro().getType(e.getKey()) instanceof Signature)  );
//
//		if(undefinedMethods) { //TODO: add list of undefined attribs
//			throw new RuntimeException("Cannot instantiate class with undefined methods!");
//		}

		// Prototypal Inheritance (like Javascript)
		CinciaObject obj = this.copy(args);
		// call the constructor on the newly created object
		obj.__init__(args); 


		//Check for declared but undefined attributes after calling the object's constructor.
		boolean undefinedAnything = obj.getEnviro().vars.entrySet().stream().anyMatch( e-> e.getValue()==null);

		if(undefinedAnything) { //TODO: add list of undefined attribs
			throw new TypeError("Cannot instantiate class with undefined attributes!");
		}

		return obj;
	}

	@Override
	public String toString() {
		return "class{}";
	}

	@Override
	public Expression simplify() {// poor Liskov :-<
		throw new RuntimeException("Cannot simplify() class!");
	}

	@Override
	public CinciaBool __eq__(CinciaObject other) {


		if(this==other) {
			return new CinciaBool(true);
		}

		var theseEntries = getEnviro().vars.entrySet();

		boolean typesMatch = theseEntries.stream()
				.filter( e-> ! e.getKey().equals(Magic.THIS.toString())    )
				.filter( e-> ! e.getKey().equals(Magic.type.toString())    )
				.allMatch( e-> {

					var thisType =	getType(e.getKey());
					var thatType =	other.getType(e.getKey() );

					if(thatType==null) {
						return false;
					}

					return thisType.matches( thatType  );

				} );

		return new CinciaBool(typesMatch);
	}

	@Override
	public boolean matches(Type other) {

		if(!(other instanceof CinciaObject)) {
			return false;
		}

		return this.__eq__((CinciaObject)other).toJava();
	}
	
	/**
	 * Returns a new class, that has the sum of the properties of this and 
	 * the other.
	 * In case of a naming conflict, the surviving property will be inherited
	 * from the OTHER class.
	 */
	@Override
	public CinciaObject __add__(CinciaObject other) {

		try {

			var otherClass =  ((CinciaObject)(Type)other);  //TODO what about JavaClass?
			var newClass = new CinciaCinciaClass(); 
			
			var thisProps = this.getEnviro()
				.vars.entrySet().stream()
				.filter( e-> !Arrays.asList(Magic.THIS.toString(), Magic.type.toString()).contains(e.getKey()) )
				.collect(Collectors.toList());
			
			var otherProps = otherClass.getEnviro()
				.vars.entrySet().stream()
				.filter( e-> !Arrays.asList(Magic.THIS.toString(), Magic.type.toString()).contains(e.getKey()) )
				.collect(Collectors.toList());

			thisProps.forEach(e-> newClass.set(e.getKey(), e.getValue(), this.getType(e.getKey())));
			otherProps.forEach(e-> newClass.set(e.getKey(), e.getValue(), otherClass.getType(e.getKey())));

			return newClass;

		} catch (ClassCastException e) {

		}

		throw new RuntimeException("Addition not implemented between class and non-class types!");
	}


	@Override
	public void set(String key, CinciaObject val) {

		// if you're assigning a function to a class-field, automatically turn it into a method !
		if(val instanceof CinciaFunction && ! (val instanceof CinciaMethod)) {
			val = CinciaMethod.fromFunction((CinciaFunction)val, this);
		}

		super.set(key, val);
	}

	@Override
	public Type unwrap() {
		return this;
	}

	@Override
	public Type resolve(Eval eval, Enviro enviro) {
		return this;
	}


}
