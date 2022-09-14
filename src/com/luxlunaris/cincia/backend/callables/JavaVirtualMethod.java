package com.luxlunaris.cincia.backend.callables;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.JavaObject;

public class JavaVirtualMethod extends JavaMethod{

	public List<JavaMethod> methods;

	public JavaVirtualMethod(JavaObject parent) {
		super(null, parent);
		methods = new ArrayList<>();
	}

	public CinciaObject run(List<CinciaObject> args) {

//		System.out.println(args);
//		System.out.println(methods.size());
//		System.out.println(methods.stream().map(m->m.method).collect(Collectors.toList()));

		CinciaObject res = null;

		for(JavaMethod m : methods) {

			//			try {
			res  = m.run(args);
			
			if(res!=null) {
				return res;
			}


			//			} catch (Exception e) {
			//
			//			}

		}

//		throw new RuntimeException("No such method!");
		return res;
	}

	public void add(JavaMethod method) {
		methods.add(method);
	}

	@Override
	public String toString() {
		return "VirtualMethod{ x"+methods.size()+" overloads }";
	}


}
