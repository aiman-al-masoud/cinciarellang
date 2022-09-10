package com.luxlunaris.cincia.backend.object;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


import com.luxlunaris.cincia.backend.callables.JavaMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

/**
 * A cicia-accessible wrapper for a generic Java object.
 * 
 * @author aiman
 */
public class JavaObject extends AbstractCinciaObject {

	public Object object; // wrapped Java object

	public JavaObject(Object object){

		super(Type.Any);
		this.type = !object.getClass().equals(Class.class.getClass())? new JavaClass(object.getClass()) : type; 
		this.object = object;

		getAccessibleMethods(object.getClass())
		.stream()
		.map(m -> new JavaMethod(m,  this))		
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

	public static List<Method> getAccessibleMethods(Class clazz) {

		if (clazz == null) {
			return Arrays.asList();
		}

		List<Method> ms = Arrays.asList(clazz.getDeclaredMethods());

		try {
			ms.addAll(getAccessibleMethods(clazz.getSuperclass()));
		} catch (Exception e) {
			
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

		}

		return Map.entry(field.getName(), new CinciaInt(-1));//TODO: fix!!!
	}

	@Override
	public Object toJava() {
		return object;
	}
	
	@Override
	protected CinciaObject getBlank() {
		return new JavaObject(object); //TODO: problem, this is not a neep copy
	}

}
