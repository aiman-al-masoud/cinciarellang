package com.luxlunaris.cincia.backend.object;

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
	

	public CinciaObject newInstance() {
		
		try {
			System.out.println("trying to instantiate "+clazz);
			return new JavaObject(clazz.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}






}
