package com.luxlunaris.cincia.backend.callables;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.JavaObject;
import com.luxlunaris.cincia.backend.throwables.CannotMutateException;

public class JavaMethod extends CinciaMethod{

	protected Method method;

	public JavaMethod(Method method, JavaObject parent) {
		super(null, parent);
		this.method = method;
		this.parent = parent;
	}

	@Override
	public CinciaObject run(List<CinciaObject> args) {

		try {

			//			System.out.println(Arrays.asList(method.getParameterTypes()));

			// convert cincia-args into java-args
			//			List<Object> javargs= args.stream().map(a->a.toJava(        )).collect(Collectors.toList());




			// convert cincia args to java objects
			List<Object> javargs = new ArrayList<>();

			if(args.size()>0) {
				Class<?>[] paramTypes = method.getParameterTypes();

				for (int i=0; i < paramTypes.length; i++) {
					Class<?> paramType = paramTypes[i];	
					Object javaArg = args.get(i).toJava( new Object[] {paramType, parent.getEnviro() /* TODO: or copy?*/} ); // cincia -> java
					javargs.add(javaArg);
				}
			}
			
			



			// store a copy of the object before method call. Made this conditional to "if parent is immutable" to avoid unneccessary overhead
			Object copy = null; //TODO: don't keep it like this!!!!! 
			if(parent.isImmutable()) {
				copy = JavaObject.deepCopy(((JavaObject)parent).object);
			}

			// invoke method on object and arguments
			Object res = method.invoke(  ((JavaObject)parent).object, javargs.toArray());

			// if hash code of object changed, it means it mutated as a result of the method call, 
			// if it was supposed to be immutable, throw an exception and revert back to previous state
			if(parent.isImmutable() &&  ((JavaObject)parent).object.hashCode() != copy.hashCode() ) {
				((JavaObject)parent).object = copy;
				throw new CannotMutateException();
			}

			if(res == null) {
				return null;
			}

			return CinciaObject.wrap(res);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassCastException e) {
			//			e.printStackTrace();
		}

		return null;
	}

	public String getName() {
		return method.getName();
	}

	@Override
	public CinciaMethod copy(List<CinciaObject> args) {
		return new JavaMethod(method, null);
	}


	@Override
	public String toString() {

		Optional<String> params = Arrays.asList(method.getParameters()).stream()
				.map(p->p.getName()+":"+p.getType())
				.reduce((s1, s2)->s1+","+s2);

		return getName()+"("+params.orElse("")+") -> "+method.getReturnType();
	}

}