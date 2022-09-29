package com.luxlunaris.cincia.backend.object;

import java.util.List;
import java.util.Map.Entry;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaClass;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaCinciaClass extends AbstractCinciaObject implements CinciaClass{

	public static String CLASS = "class";

	public CinciaCinciaClass() {
		super(new IdentifierType("Class"));
		type = this; // a class is its own type
		enviro.set(CLASS, this); // mark the env as belonging to a class
	}

	public void declareAttribute(String name, Type type) {
		set(name, null, type);
	}	

	public void declareMethod(String name, Signature type) {
		set(name, null, type);
	}	

	public void addMethod(String name, CinciaMethod method) {
		set(name, method, method.type);
	}	

	public void addAttribute(String name, AbstractCinciaObject value) {
		set(name, value, value.type);
	}	

	//TODO: useless!
	public void addInterface(CinciaInterface cincinterface) {

		cincinterface.getDeclarations().forEach(d->{

			try {

				get(d.getName());

			} catch (Exception e) {
				throw new RuntimeException("Required field from interface missing!");
			}

		});

	}

	/**
	 * Creates and returns a new object of the same type as this class.
	 * @param args
	 * @return
	 */
	@Override
	public CinciaObject newInstance(List<CinciaObject> args) {


		//TODO: deal with modifiers such as static

		// Prototypal Inheritance (like Javascript)
		CinciaObject obj = this.copy(args);
		//TODO: deal with null values, null values could be declared 
		// attributes, or declared functions. Either way they could come
		// from interfaces. 

		obj.__init__(args);
		return obj;
	}

	@Override
	public String toString() {
		return "class{}";
	}

	@Override
	public Expression simplify() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean matches(Type other) {

		try {
			return this == ((CinciaCinciaClass)other); // matches in RAM


			//TODO: check mixins, method names, interfaces, idk what I'm doing, etc...


		} catch (ClassCastException e) {

		}

		return false;

	}



	@Override
	public CinciaObject __add__(CinciaObject other) {

		//TODO: enviro.get broken for declared but undefined vals


		try {

			var dontCare =  (Type)other; // TODO what about JavaClass?
			var otherClass =  (CinciaObject)other;
			var c = new CinciaCinciaClass();


			for( Entry<String, CinciaObject> entry : this.getEnviro().vars.entrySet() ) {

				//				System.out.println(entry);

				if(entry.getKey().equals( Magic.THIS.toString())) {
					continue;
				}

				if(entry.getKey().equals( "class" )) {
					continue;
				}

				// TODO: entry.getValue() could be null, and so calling getType() on it
				// throws a NullPointerEx...
				c.set(entry.getKey(), entry.getValue(), otherClass.getEnviro().getType(entry.getKey()));
			}

			for( Entry<String, CinciaObject> entry : otherClass.getEnviro().vars.entrySet() ) {

				//				System.out.println(entry);

				if(entry.getKey().equals( Magic.THIS.toString())) {
					continue;
				}

				if(entry.getKey().equals( "class" )) {
					continue;
				}

				// TODO: entry.getValue() could be null (interfaces), and so calling getType() on it
				// throws a NullPointerEx...
				c.set(entry.getKey(), entry.getValue(), otherClass.getEnviro().getType(entry.getKey()));
			}

			return c;

		} catch (ClassCastException e) {

		}

		throw new RuntimeException("Addition not implemented between class and non-class types!");
	}


	@Override
	public void set(String key, CinciaObject val) {

		// if you're assigning a function to a class-field, turn it into a method 
		if(val instanceof CinciaFunction && ! (val instanceof CinciaMethod)) {
			val = CinciaMethod.fromFunction((CinciaFunction)val, this);
		}

		super.set(key, val);
	}






}
