package com.luxlunaris.cincia.backend.callables;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
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

	public void add(JavaMethod method) {
		methods.add(method);
	}

	@Override
	public String toString() {
		return "OverloadedMethod{ x"+methods.size()+" overloads }";
	}


}
