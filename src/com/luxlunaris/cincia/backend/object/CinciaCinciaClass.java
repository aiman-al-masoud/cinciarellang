package com.luxlunaris.cincia.backend.object;

import java.util.List;
import java.util.Map.Entry;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaClass;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaCinciaClass extends AbstractCinciaObject implements CinciaClass{

	public static String CLASS = "class";

	public CinciaCinciaClass() {
		super(new IdentifierType("Class"));
		type = this; // a class is its own type
		enviro.set(CLASS, this); // mark the env as belonging to a class //TODO maybe not necessary anymore, use 'type'
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

		//Check for declared but undefined methods on this class
		//before creating a copy (instance/object).
		boolean undefinedMethods = this.getEnviro().vars.entrySet().stream().anyMatch( e-> e.getValue()==null && (this.getEnviro().getType(e.getKey()) instanceof Signature)  );

		if(undefinedMethods) {
			throw new RuntimeException("Cannot instantiate class with undefined methods!");
		}

		// Prototypal Inheritance (like Javascript)
		CinciaObject obj = this.copy(args);

		//		System.out.println(args);
		obj.__init__(args); //TODO: fix types and type-inference, maybe that's the problem

		//		System.out.println(obj.getEnviro().values());

		//Check for declared but undefined attributes after 
		//calling the object's constructor.
		boolean undefinedAnything = this.getEnviro().vars.entrySet()
				.stream()
				.anyMatch( e-> {
//					System.out.println(e.getKey()+" "+(e.getValue()==null));
					return e.getValue()==null;
				});

		if(undefinedAnything) {
			throw new RuntimeException("Cannot instantiate class with undefined attributes!");
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

		//		System.out.println(((CinciaCinciaClass)other).getEnviro().vars  );

		//		System.out.println("same object? "+ (this==other));

		if(this==other) {
			return new CinciaBool(true);
		}

		var theseEntries = getEnviro().vars.entrySet();
		//		var otherEntries =  ((CinciaCinciaClass)other).getEnviro().vars.entrySet();

		boolean typesMatch = theseEntries.stream()
				.filter( e-> ! e.getKey().equals(Magic.THIS.toString())    )
				.filter( e-> ! e.getKey().equals("type")    )
				.filter( e-> ! e.getKey().equals(CLASS)    )
				.allMatch( e-> {

					var thisType =	getType(e.getKey());
					var thatType =	other.getType(e.getKey() );

					if(thatType==null) {
						//												System.out.println(e.getKey()+" "+e.getValue());
						return false;
					}

					var b = thisType.matches( thatType  );
					//											System.out.println(e.getKey()+" "+b);

					//											System.out.println(e.getKey()+" "+b);

					return b;
				} );


		//		System.out.println("types match "+typesMatch+" this: "+this.hashCode()+" other: "+other.hashCode());

		return new CinciaBool(typesMatch);
		//		for (int i=0; i<theseEntries.size(); i++) {
		//			
		//		}

		//		return super.__eq__(other);
	}

	@Override
	public boolean matches(Type other) {

		//		System.out.println(((CinciaCinciaClass)other).getEnviro().vars  );

		//TODO: how about an equality check based on actual fields and methods? Like
		//a more "institutionalized" form of duck-typing?

		//		try {
		//			return this == ((CinciaCinciaClass)other); // matches in RAM
		//
		//
		//			//TODO: check mixins, method names, interfaces, idk what I'm doing, etc...
		//
		//
		//		} catch (ClassCastException e) {
		//
		//		}
		//
		//		return false;

		return this.__eq__((CinciaObject)other).toJava();

	}



	@Override
	public CinciaObject __add__(CinciaObject other) {

		//TODO: enviro.get broken for declared but undefined vals


		try {

			var dontCare =  (Type)other; 
			var otherClass =  (CinciaObject)other;  // could be a class or an interface //TODO what about JavaClass?
			var c = new CinciaCinciaClass(); //TODO always produce a class?


			for( Entry<String, CinciaObject> entry : this.getEnviro().vars.entrySet() ) {

				//				System.out.println(entry);

				if(entry.getKey().equals( Magic.THIS.toString())) {
					continue;
				}

				if(entry.getKey().equals( "class" )) {
					continue;
				}

				if(entry.getKey().equals( "type" )) {
					continue;
				}

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

				if(entry.getKey().equals( "type" )) {
					continue;
				}

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
