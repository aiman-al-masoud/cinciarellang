package com.luxlunaris.cincia.backend.callables;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.JavaObject;
import com.luxlunaris.cincia.backend.throwables.CannotMutateException;

public class JavaMethod extends CinciaMethod{

	protected Method method;

	public JavaMethod(Method method, CinciaObject parent) {
		super(null);
		this.method = method;
		this.parent = parent;
	}

	@Override
	public CinciaObject run(List<CinciaObject> args) {

		// if the object this method is called on is immutable, 
		// prevent void and no-arg methods from running, 
		// because they're almost certainly side-effect laden.
		if(parent.isImmutable()) {

			boolean isVoid = method.getReturnType().equals(Void.TYPE);
			boolean isNoArgs =  method.getParameters().length==0;

			if(isVoid || isNoArgs) {
				throw new CannotMutateException();
			}

		}


		try {

			// convert cincia-args into java-args
			List<Object> javargs= args.stream().map(a->a.toJava()).collect(Collectors.toList());
			
			//store hash code of object
			int hashBefore = ((JavaObject)parent).object.hashCode();
 			
			// invoke method on object and arguments
			Object res = method.invoke(  ((JavaObject)parent).object, javargs.toArray());
			
			// if hash code of object changed, it means it mutated, if it was supposed to be immutable, throw an exception
			if( parent.isImmutable() &&  ((JavaObject)parent).object.hashCode()!=hashBefore ) {
				throw new CannotMutateException();
			}

			if(res ==null) {
				return null;
			}

			return CinciaObject.wrap(res);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getName() {
		return method.getName();
	}


	@Override
	public CinciaMethod copy(List<CinciaObject> args) {
		return new JavaMethod(method, parent);
	}



}