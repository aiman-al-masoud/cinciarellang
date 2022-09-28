package com.luxlunaris.cincia.backend.callables;

import java.util.ArrayList;
import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.Callable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.interfaces.Eval;
import com.luxlunaris.cincia.backend.interfaces.WrappedFunction;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.object.Magic;
import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public class CinciaFunction extends AbstractCinciaObject implements Callable{

	//TODO: make an interface and a better abstract class, add option to use 
	// java.reflect.Method in place of lambdex or wrappedFunction.

	protected LambdaExpression lambdex;
	protected List<Parameter> params;
	protected Eval eval;
	protected WrappedFunction wrappedFunction;

	public CinciaFunction(LambdaExpression lambdex, Eval eval) {

		super(lambdex!=null? lambdex.signature : new IdentifierType("NativeCodeFunc"));
		this.eval = eval;
		this.lambdex = lambdex;
		params = parseParams(lambdex);			
	}

	public CinciaFunction(WrappedFunction wrappedFunction) {
		super(new IdentifierType("WrappedFunction")); //TODO: extract Id, NativeType
		this.wrappedFunction = wrappedFunction;
	}

	public CinciaObject run(List<CinciaObject> args, Enviro enviro) {


		if(args != null && wrappedFunction == null) {

			// TODO: check param/args number

			// Bind args to environment
			for(int i=0; i < params.size(); i++) {

				Parameter p = params.get(i);
				CinciaObject arg = args.get(i);
				enviro.set(p.name, p.isByRef()? arg : arg.copy(args), p.type);	
			}

		}


		if(lambdex == null && wrappedFunction != null) {
			return wrappedFunction.run(args);
		}else if(lambdex != null && lambdex.block != null) {
			return eval.eval(lambdex.block, enviro);
		}else if(lambdex != null && lambdex.expression != null) {
			return eval.eval(lambdex.expression, enviro);
		}

		throw new RuntimeException("Lambda without expression nor block!");
	}

	public static List<Parameter> parseParams(LambdaExpression lambdex) {

		List<Parameter> result = new ArrayList<Parameter>();
		
		// no params if no lambda expression or lambda exp without inputs
		if(lambdex==null || lambdex.signature.params == null) {
			return result;
		}

//		List<SingleDeclaration> declarations = new ArrayList<SingleDeclaration>();
		
		List<SingleDeclaration> declarations = 
		lambdex.signature.params.toList();

//		try {
//			SingleDeclaration sD =(SingleDeclaration)lambdex.signature.params;
//			declarations.add(sD);
//		}catch (ClassCastException e) {
//			MultiDeclaration mD =(MultiDeclaration)lambdex.signature.params;
//			declarations.addAll(mD.declarations);
//		}


		declarations.forEach(d->{
			result.add(new Parameter(d.getName(), d.getType(), d.getModifiers()));
		});

		return result;
	}

	public static CinciaFunction make(LambdaExpression lambdex, Eval eval) {

		List<Parameter> params = parseParams(lambdex);

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
		return this; 
	}


	@Override
	public String toString() {
		return lambdex ==null?  "NativeCode()"   :  lambdex.signature.toString();
	}




}
