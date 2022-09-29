package com.luxlunaris.cincia.backend.object;

import java.util.List;
import java.util.Map.Entry;

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

		try {

			CinciaCinciaClass otherClass = (CinciaCinciaClass)other; // TODO what about JavaClass?
			CinciaCinciaClass c = new CinciaCinciaClass();


			for( Entry<String, CinciaObject> entry : this.getEnviro().vars.entrySet() ) {
				
//				System.out.println(entry);
				
				if(entry.getKey().equals( Magic.THIS.toString())) {
					continue;
				}
				
				if(entry.getKey().equals( "class" )) {
					continue;
				}
				
				c.set(entry.getKey(), entry.getValue());
			}

			for( Entry<String, CinciaObject> entry : otherClass.getEnviro().vars.entrySet() ) {
				
//				System.out.println(entry);
				
				if(entry.getKey().equals( Magic.THIS.toString())) {
					continue;
				}
				
				if(entry.getKey().equals( "class" )) {
					continue;
				}
				
				c.set(entry.getKey(), entry.getValue());
			}

			return c;

		} catch (ClassCastException e) {

		}

		throw new RuntimeException("Addition not implemented between class and non-class types!");
	}


}
