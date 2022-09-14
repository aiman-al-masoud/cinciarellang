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
import com.luxlunaris.cincia.backend.callables.JavaVirtualMethod;
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

		getAccessibleAttributes(object.getClass())
		.stream()
		.map(a -> convertField(a, object))
		.forEach(e->{
			set(e.getKey(), e.getValue());
		});
		
		
		getAccessibleMethods(object.getClass())
		.stream()
		.map(m -> new JavaMethod(m,  this))		

		.forEach(m->{

			try {
				// if name was already taken
				JavaMethod oldMethod = (JavaMethod)get(m.getName());

				// if taken by a virtual method
				if(oldMethod instanceof JavaVirtualMethod) {

					JavaVirtualMethod oldVm = (JavaVirtualMethod) oldMethod;
					oldVm.add(m);

				}else {
					
					// TODO
					if(m.getName().equals("nextInt")) {
						System.out.println("creating new VirtualMethod for nextInt!!!!");
					}
					
					// if taken by a regular method
					JavaVirtualMethod vm = new JavaVirtualMethod(this);
					vm.add(oldMethod);
					set(m.getName(), vm, Type.Any); 
				}


			} catch (Exception e) {

				// if name was never taken before, add non-virtual method
				set(m.getName(), m, Type.Any); 

			}


		});


	}

	public static List<Method> getAccessibleMethods(Class clazz) {

		if (clazz == null) {
			return Arrays.asList();
		}

		List<Method> ms = new ArrayList<>();
		ms.addAll(Arrays.asList(clazz.getDeclaredMethods()));
		ms.addAll(getAccessibleMethods(clazz.getSuperclass()));
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
	public CinciaObject copy(List<CinciaObject> args) {
		return new JavaObject(deepCopy(object));
	}

	public static Object deepCopy(Object object) {

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
			return copy;

		} catch (Exception e) {

		}

		throw new RuntimeException("Couldn't deep-copy java object!");


	}









}
