package com.luxlunaris.cincia.backend.object;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


import com.luxlunaris.cincia.backend.callables.JavaMethod;
import com.luxlunaris.cincia.backend.callables.JavaOverloadedMethod;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

/**
 * A cicia-accessible wrapper for a generic Java object.
 * 
 * @author aiman
 */
public class JavaObject extends BaseCinciaObject {

	public Object object; // wrapped Java object

	public JavaObject(Object object){

		super(Type.Any);
		this.type = !object.getClass().equals(Class.class.getClass())? new JavaClass(object.getClass()) : type; 
		this.object = object;

		//		System.out.println("JavaObject "+object);

		// if wrapped object is already a class, don't get its class (Class)...
		Class clazz = object instanceof Class? (Class) object : object.getClass();

		getAccessibleMethods(clazz)
		.stream()
		.map(m -> new JavaMethod(m,  this))		

		.forEach(m->{

			//			System.out.println(m.getName());

			try {
				// if name was already taken
				JavaMethod oldMethod = (JavaMethod)get(m.getName());

				// if taken by a virtual method
				if(oldMethod instanceof JavaOverloadedMethod) {

					JavaOverloadedMethod oldVm = (JavaOverloadedMethod) oldMethod;
					oldVm.add(m);

				}else {

					// if taken by a regular method
					JavaOverloadedMethod vm = new JavaOverloadedMethod(this);
					vm.add(oldMethod);
					vm.add(m);
					set(m.getName(), vm, Type.Any); 

				}


			} catch (Exception e) {

				// if name was never taken before, add non-virtual method
				set(m.getName(), m, Type.Any); 

			}

		});

		getAccessibleAttributes(clazz)
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

		//		System.out.println(clazz);

		List<Method> ms = new ArrayList<>();
		ms.addAll(Arrays.asList(clazz.getDeclaredMethods()));
		//		ms.addAll(Arrays.asList(clazz.getMethods()));
		//TODO just getMethods() no recursion? Nope only public
		ms.addAll(getAccessibleMethods(clazz.getSuperclass()));
		return ms;

	}

	public static List<Field> getAccessibleAttributes(Class clazz) {

		List<Field> allFields = Arrays.asList(clazz.getDeclaredFields()).stream().filter( a-> !Modifier.isPrivate(a.getModifiers())  && !Modifier.isProtected(a.getModifiers())   ).collect(Collectors.toList());
		//		allFields.addAll(Arrays.asList(clazz.getFields()).stream().filter( a-> !Modifier.isPrivate(a.getModifiers())  && !Modifier.isProtected(a.getModifiers())   ).collect(Collectors.toList()));
		return allFields;

	}


	public static Entry<String, CinciaObject> convertField(Field field, Object object){

		try {
			return Map.entry(field.getName(), CinciaObject.wrap(field.get(object)));
		} catch (IllegalArgumentException | IllegalAccessException e) {

		}

		return Map.entry(field.getName(), CinciaObject.wrap(-1));//TODO: fix!!!
	}

	@Override
	public Object toJava() {
		return object;
	}

	@Override
	public CinciaObject copy(List<CinciaObject> args) {
		return new JavaObject(deepCopy(object));
	}

	/**
	 * Returns a new deep copy of an object, the original
	 * must be Serializable!
	 * @param object
	 * @return
	 */
	public static Object deepCopy(Object object) {

		//		System.out.println("copying "+object);

		try {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.flush();
			oos.close();
			bos.close();
			byte[] byteData = bos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
			Object copy = new ObjectInputStream(bais).readObject();

			//			System.out.println("copied: "+copy);
			//TODO: check if copy if perfect by comparing hash codes, there could be trainsient attribs 

			return copy;

		} catch (Exception e) {
			//System.out.println("tried copying java object");
		}


		throw new RuntimeException("Couldn't deep-copy java object!");


	}

	@Override
	public CinciaBool __eq__(CinciaObject other) {


		try {
			JavaObject o = (JavaObject)other;
			return new CinciaBool(object.equals(o.object));
		} catch (ClassCastException e) {

		}

		return new CinciaBool(false);
	}


}
