package com.luxlunaris.cincia.backend.callables;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.JavaObject;

public class JavaMethod extends CinciaMethod{

	protected Method method;

	public JavaMethod(Method method, CinciaObject parent) {
		super(null);
		this.method = method;
		this.parent = parent;
	}

	@Override
	public CinciaObject run(List<CinciaObject> args) {

		try {

			List<Object> javargs= args.stream().map(a->a.toJava()).collect(Collectors.toList());
			return CinciaObject.wrap(method.invoke(  ((JavaObject)parent).object ,   javargs.toArray()));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getName() {
		return method.getName();
	}

}