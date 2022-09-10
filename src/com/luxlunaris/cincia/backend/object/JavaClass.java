package com.luxlunaris.cincia.backend.object;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class JavaClass extends JavaObject implements Type{

	Class clazz;
	

	public JavaClass(Class clazz) {
		super(clazz);
		this.clazz = clazz;
	}


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
		return other==this; //TODO: improve
	}


	@Override
	public Expression simplify() {
		return null;
	}




}
