package com.luxlunaris.cincia.backend.object;

import java.lang.reflect.Constructor;
import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaClass;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class JavaClass extends JavaObject implements CinciaClass{

	protected Class clazz;

	public JavaClass(Class clazz) {
		super(clazz);
		this.clazz = clazz;
		this.type = this;
	}

	@Override
	public CinciaObject newInstance(List<CinciaObject> args) {

		// try every cons until it works with the supplied args ...
		for(Constructor cons : clazz.getDeclaredConstructors()) {

			try {
				Object newInst = cons.newInstance(args.stream().map(a-> a.toJava()).toArray());
				return new JavaObject(newInst);					
			} catch (Exception e) {

			}

		}

		// ... if no cons matches the args
		throw new RuntimeException("Cannot create Java class, no matching constructor for supplied args: "+args+"");
	}

	/**
	 * This class matches the other if this is a superclass of the other,
	 * or they are the same class.
	 */
	@Override
	public boolean matches(Type other) {

		try {

			JavaClass jclass =  (JavaClass)other;
			boolean thisIsSameOrSuper = clazz.isAssignableFrom(jclass.clazz);			
			return thisIsSameOrSuper;

		} catch (ClassCastException e) {

		}

		return false;
	}

	/**
	 * Calling a class means creating a new instance.
	 */
	@Override
	public CinciaObject run(List<CinciaObject> args, Enviro enviro) {
		return newInstance(args);
	}

}
