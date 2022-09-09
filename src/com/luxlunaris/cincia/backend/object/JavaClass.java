package com.luxlunaris.cincia.backend.object;

import java.lang.reflect.InvocationTargetException;
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
		
		System.out.println(args);
		
		try {
			System.out.println("trying to instantiate "+clazz);
			@SuppressWarnings("unchecked")
//			clazz.getClasses()
			Object newInst = clazz.getDeclaredConstructor().newInstance( args.stream().map(a-> a.toJava()).toArray()  );
//			return new JavaObject(clazz.newInstance());
			return new JavaObject(newInst);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		return null;
	}






}
