package com.luxlunaris.cincia.backend.object;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class JavaObject extends AbstractCinciaObject {

	Object object;
	
//	public static Object instantiate(String javaClass) {
//		
//		try {
//			return JavaObject.class.getClassLoader().loadClass(javaClass).newInstance();
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}

//	public JavaObject(String javaClass){
//		this(instantiate(javaClass));
//	}

	public JavaObject(Object object){
		
		super(Type.Any);
		this.object = object;
		
		getAccessibleMethods(object.getClass())
		.stream()
		.map(m->new JavaMethod(m, this))
		.forEach(m->{
			set(m.getName(), m, Type.Any);
		});
		
		
		getAccessibleAttributes(object.getClass())
		.stream()
		.map(a -> convertField(a, object))
		.forEach(e->{
			set(e.getKey(), e.getValue());
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
				
				List<Object> javargs= args.stream().map(a->a.toJava()).collect(Collectors.toList());
//				System.out.println(javargs);
				return CinciaObject.wrap(method.invoke(  ((JavaObject)parent).object ,   javargs.toArray()));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException e) {
//				System.out.println("as I predicted!!!");
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
		
		try {
			
			ms.addAll(getAccessibleMethods(clazz.getSuperclass()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return ms;

	}
	
	public static List<Field> getAccessibleAttributes(Class clazz) {
		return Arrays.asList(clazz.getDeclaredFields()).stream().filter( a-> !Modifier.isPrivate(a.getModifiers())  && !Modifier.isProtected(a.getModifiers())   ).collect(Collectors.toList());
	}
	
	
	public static Entry<String, CinciaObject> convertField(Field field, Object object){
		
		try {
			return Map.entry(field.getName(), CinciaObject.wrap(field.get(object)));
		} catch (IllegalArgumentException | IllegalAccessException e) {
//			e.printStackTrace();
//			System.out.println(field.getName());
		}
		
		return Map.entry(field.getName(), new CinciaInt(-1));
	}
	
	
	

	@Override
	public Object toJava() {
		return this;
	}

}
