package com.luxlunaris.cincia.backend.object;

import java.util.List;

import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaClass extends AbstractCinciaObject implements Type{

	public static String CLASS = "class";

	public CinciaClass() {
		super(new IdentifierType("Class"));
		type =this;
		enviro.set(CLASS, this);
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



	public CinciaObject constructor(List<CinciaObject> args) {

		//TODO: deal with modifiers such as static

		// basically Prototypal Inheritance (like Javascript)
		CinciaObject obj = this.copy(args);


		// java-code wrappers need to point to the original instance of the new java object
		obj.set("this", obj, type); 
		CinciaMethod m1 = new CinciaMethod(obj::copy);
		m1.parent = obj;
		obj.set(Magic.copy, m1);
		CinciaMethod m2 = new CinciaMethod(obj::freeze);
		m2.parent = obj;
		obj.set(Magic.freeze, m2);
		//TODO: probably wrong for the into method, since it can be overridden:
		CinciaMethod m3 = new CinciaMethod(obj::into); 
		m3.parent = obj;
		obj.set(Magic.into, m3);

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
			return this == ((CinciaClass)other); // matches in RAM


			//TODO: check extends etc...


		} catch (ClassCastException e) {

		}

		return false;

	}


}
