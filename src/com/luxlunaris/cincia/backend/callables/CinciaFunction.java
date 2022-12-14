package com.luxlunaris.cincia.backend.callables;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.interfaces.Callable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.interfaces.WrappedFunction;
import com.luxlunaris.cincia.backend.object.BaseCinciaObject;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.throwables.UndefinedError;
import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public class CinciaFunction extends BaseCinciaObject implements Callable{

	
	//TODO: all functions must declare a signature, no "NativeCode" type.
	//TODO: in stdlib, in magic methods etc ...

	protected LambdaExpression lambdex;
	protected List<Parameter> params;
	protected Eval eval;
	protected WrappedFunction wrappedFunction;

	public CinciaFunction(LambdaExpression lambdex, Eval eval) {

		super(new TypeWrapper(lambdex.signature));
		this.eval = eval;
		this.lambdex = lambdex;
		params = initParams(lambdex);	
		setImmutable(); // functions are immutable
	}

	public CinciaFunction(WrappedFunction wrappedFunction, Signature signature) {
		super(new TypeWrapper(signature));
		this.wrappedFunction = wrappedFunction;
	}

	public CinciaFunction(WrappedFunction wrappedFunction) { //TODO: phase out this constructor without a signature
		super(new IdentifierType("NativeCode")); //TODO: put an actual signature here
		this.wrappedFunction = wrappedFunction; //TODO any chance of auto-detecting signature of native wrapped function?
	}

	public CinciaObject run(List<CinciaObject> args, Enviro enviro) {

		if(args != null && wrappedFunction == null) {

			// TODO: check param/args number
			
			int bindNum = lambdex.explicitParams? params.size() : args.size();
//			int bindNum = Math.min(args.size(), params.size());

			
//			System.out.println("args: "+args);
//			System.out.println("params: "+params);
//			System.out.println("bind num: "+bindNum);

			// Bind args to environment
			for(int i=0; i < bindNum; i++) {
				Parameter p =  i<params.size()?  params.get(i) : new Parameter("_", Type.Any, Arrays.asList());				
				CinciaObject arg = args.get(i);
				enviro.set(p.name, p.isByRef()? arg : arg.copy(args),  p.type);	
			}

		}
		
		
		try {
			if(lambdex == null && wrappedFunction != null) {
				return wrappedFunction.run(args);
			}else if(lambdex != null) {
				return eval.eval(lambdex.runnable, enviro);
			}
			
		} catch (UndefinedError e) {
			//STUPID: run multiple times catching undefined var error and define next var with arg each time
			//TODO: STUPID, can lead to Stackoverflow exception!
//			e.printStackTrace();
//			System.exit(-1);
			params.add(new Parameter( e.undefinedName , Type.Any ,  Arrays.asList() ));
			return run(args, enviro);
		}

		
		//TODO:add check on return value type!
		
		throw new RuntimeException("Failed to run lambda!");
	}

	public static List<Parameter> initParams(LambdaExpression lambdex) {

		// no params if no lambda expression or lambda exp without inputs
		if(lambdex==null || lambdex.signature.params == null) {
			return Arrays.asList(); // empty list
		}

		List<SingleDeclaration> declarations = lambdex.signature.params.toList();

		return declarations.stream()
				.map(d-> new Parameter(d.getName(), d.getType(), d.getModifiers()))
				.collect(Collectors.toList());

	}

	public static CinciaFunction make(LambdaExpression lambdex, Eval eval) {

		List<Parameter> params = initParams(lambdex);

		boolean takesArgByRef = params.stream().anyMatch(p->p.modifiers.contains(Modifiers.REF));
		boolean readsFromExtScope = lambdex.modifiers.contains(Modifiers.NONLOCAL);

		if(takesArgByRef || readsFromExtScope) {
			return new CinciaFunction(lambdex, eval);
		}

		//... else it's a pure function by default
		return new PureCinciaFunction(lambdex, eval);
	}

	public boolean isNativeCode() {
		return wrappedFunction != null;
	}

	@Override
	public CinciaObject copy(List<CinciaObject> args) {
		return this; // since functions are immutable
	}

	@Override
	public String toString() {
		return type+"";
	}

}
