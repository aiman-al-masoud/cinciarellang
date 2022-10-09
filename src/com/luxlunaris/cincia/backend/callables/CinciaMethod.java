package com.luxlunaris.cincia.backend.callables;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.interfaces.WrappedFunction;
import com.luxlunaris.cincia.backend.object.BaseCinciaObject;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;


public class CinciaMethod extends CinciaFunction{

	public CinciaObject parent;

	public CinciaMethod(LambdaExpression lambdex, Eval eval) {
		super(lambdex, eval);
	}

	public CinciaMethod(WrappedFunction wrappedFunction, CinciaObject parent) {
		super(wrappedFunction);
		this.parent = parent;
	}

	public void setParent(BaseCinciaObject parent) {
		this.parent = parent;
	}

	/**
	 * Takes the new parent as the first element of the args list.
	 */
	@Override
	public CinciaMethod copy(List<CinciaObject> args) {

		CinciaMethod copy;
		CinciaObject newParent = args.size() > 0 ? args.get(0) : null;

		if(isNativeCode()) { // "native" means that method is alreay on newParent created with getBlank()
			return this; //TODO: wrong, must return method instance on "newParent"
		}

		copy = new CinciaMethod(lambdex, eval); //same code...
		copy.parent = newParent; //... different parent

		return copy;
	}

	/**
	 * Can have side effects on the parent's env when using this
	 * to access its vars.
	 * @param args
	 * @param eval
	 * @return
	 */
	public CinciaObject run(List<CinciaObject> args) {	
		//TODO  PROBLEM 1: this overwrites also stuff in this given how this was implemented
		//TODO: PROBLEM 2: recursive methods are broken, because all calls on the stack refer to the same environment!!
		//TODO: Fixed PROBLEM 3 by shallow-cloning env, but now you can't set instance vars without using this.
		return super.run(args, parent.getEnviro().shallowCopy());
	}

	@Override
	public Object toJava(Object... args) {

		// cast first arg to a (functional) interface
		Class<?> anInterface = (Class<?>)args[0];

		// env comes from parent object
		return makeProxy(anInterface);

	}


	public Object makeProxy(Class<?> anInterface) {

		// build a dynamic proxy 
		Object instance = Proxy.newProxyInstance(anInterface.getClassLoader(), new Class<?>[]{anInterface}, new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

				// ... the proxy implements the first method in the (functional) interface
				String methodName = anInterface.getMethods()[0].getName();

				if(method.getName().equals(methodName)){

					// convert java args into cincia objects
					List<CinciaObject> cinciargs = Arrays.asList(args).stream().map(o->CinciaObject.wrap(o)).collect(Collectors.toList());

					// run this function //TODO: fix enviro problem
					return run(cinciargs);

				}else {
					return null;
				}

			}
		}); 

		return instance;

	}

	/**
	 * Bind a function to an object, return a new method.
	 * @param f
	 * @param parent
	 * @return
	 */
	public static CinciaMethod fromFunction(CinciaFunction f, CinciaObject parent) {

		CinciaMethod cm = new CinciaMethod(f.lambdex, f.eval);
		cm.parent = parent;
		return cm;

	}


}
