package com.luxlunaris.cincia.backend.object;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaClass;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class JavaClass extends JavaObject implements CinciaClass{

	Class clazz;


	public JavaClass(Class clazz) {
		super(clazz);
		this.clazz = clazz;
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
		return null;
	}


	@Override
	public boolean matches(Type other) {

		try {

			JavaClass jclass =  (JavaClass)other;
			boolean otherIsSuper = jclass.clazz.isAssignableFrom(clazz);
			boolean otherIsSame = jclass.clazz.equals(clazz);
			return otherIsSame | otherIsSuper;

		} catch (ClassCastException e) {

		}

		return false;
	}


	@Override
	public Expression simplify() {
		return null;
	}




}
