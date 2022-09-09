package com.luxlunaris.cincia.backend.object;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class JavaObject extends AbstractCinciaObject {

	Object object;

	public JavaObject(String javaClass) throws Exception{
		this(JavaObject.class.getClassLoader().loadClass(javaClass).newInstance());
	}

	public JavaObject(Object object) {
		super(Type.Any);
		this.object = object;
		
		getAccessibleMethods(object.getClass())
		.stream()
		.map(m->new JavaMethod(m, this))
		.forEach(m->{
			set(m.getName(), m, Type.Any);
		});

	}
	

	class JavaMethod extends CinciaMethod{

		protected Method method;

		public JavaMethod(Method method, CinciaObject parent) {
			super(null);
			this.method = method;
			this.parent = parent;
		}

		@Override
		public CinciaObject run(List<CinciaObject> args) {

			try {
				return CinciaObject.create(method.invoke(parent, args.toArray()));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException e) {
				System.out.println("as I predicted!!!");
				e.printStackTrace();
			}

			return null;
		}

		public String getName() {
			return method.getName();
		}

	}


	public static List<Method> getAccessibleMethods(Class clazz) {

		if (clazz == null) {
			return Arrays.asList();
		}

		List<Method> ms = Arrays.asList(clazz.getDeclaredMethods());
		ms.addAll(getAccessibleMethods(clazz.getSuperclass()));
		return ms;

	}

	@Override
	public Object toJava() {
		return this;
	}

}
