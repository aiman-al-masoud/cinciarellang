package com.luxlunaris.cincia.backend.object;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;

public class JavaClass extends JavaObject {

	Class clazz;

	public static Class loadClass(String javaClass) {

		try {
			return JavaClass.class.getClassLoader().loadClass(javaClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public JavaClass(String javaClass) throws ClassNotFoundException {
		super(loadClass(javaClass));
	}

	public JavaClass(Class clazz) {
		super(clazz);
		this.clazz = clazz;
	}


	public CinciaObject newInstance(List<CinciaObject> args) {

		// try every cons until it works with the supplyied args ...
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






}
