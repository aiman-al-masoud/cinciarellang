package com.luxlunaris.cincia.backend.callables;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.JavaObject;

public class JavaVirtualMethod extends JavaMethod{

	List<JavaMethod> methods;

	public JavaVirtualMethod(JavaObject parent) {
		super(null, parent);
		methods = new ArrayList<>();
	}

	public CinciaObject run(List<CinciaObject> args) {

		for(JavaMethod m : methods) {

			try {
				return m.run(args);
			} catch (Exception e) {

			}

		}

		throw new RuntimeException("No such method!");
	}

	public void add(JavaMethod method) {
		methods.add(method);
	}
	

}
