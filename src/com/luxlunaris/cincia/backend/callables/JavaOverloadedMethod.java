package com.luxlunaris.cincia.backend.callables;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.object.JavaObject;

public class JavaOverloadedMethod extends JavaMethod{

	protected List<JavaMethod> methods;

	public JavaOverloadedMethod(JavaObject parent) {
		super(null, parent);
		methods = new ArrayList<>();
	}

	public CinciaObject run(List<CinciaObject> args) {

		CinciaObject res = null;

		for(JavaMethod m : methods) {

			res  = m.run(args);

			if(res!=null) {
				return res;
			}

		}

		return res;
	}
	
	@Override
	public CinciaObject run(List<CinciaObject> args, Enviro enviro) {
		return run(args);
	}

	public void add(JavaMethod method) {
		methods.add(method);
	}
	
	@Override
	public String getName() {
		return methods.get(0).getName();
	}

	@Override
	public String toString() {		
		return getName()+"( x"+methods.size()+" overloads ) -> "+methods.get(0).method.getReturnType();
	}


}
