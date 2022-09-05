package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.luxlunaris.cincia.backend.Interpreter.Eval;
import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public class CinciaFunction extends AbstractCinciaObject implements Callable{

	@FunctionalInterface
	interface WrappedFunction{
		CinciaObject run(List<CinciaObject> args);
	}

	protected LambdaExpression lambdex;
	protected List<Parameter> params;
	protected Eval eval;
	protected WrappedFunction wrappedFunction;

	public CinciaFunction(LambdaExpression lambdex, Eval eval) {
		super(lambdex.signature);
		this.eval = eval;
		this.lambdex = lambdex;
		params = new ArrayList<Parameter>();
		parseParams();
	}

	public CinciaFunction(WrappedFunction wrappedFunction) {
		super(new IdentifierType("WrappedFunction")); //TODO: extract Id, NativeType
		this.wrappedFunction = wrappedFunction;
	}

	public CinciaObject run(List<CinciaObject> args, Enviro enviro) {


		if(args != null && wrappedFunction ==null) {

			
			// TODO: check matching types
			// TODO: check (to be added) ref keyword to determine whether to pass by reference or value
			
			// bind args to env
			for(int i=0; i < params.size(); i++) {
				
				Parameter p = params.get(i);
				CinciaObject arg = args.get(i);
				
				enviro.set(p.name, arg, p.type);
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

	public void parseParams() {

		if(lambdex==null) {
			return;
		}

		List<SingleDeclaration> declarations = new ArrayList<SingleDeclaration>();

		try {
			SingleDeclaration sD =(SingleDeclaration)lambdex.signature.params;
			declarations.add(sD);
		}catch (ClassCastException e) {
			MultiDeclaration mD =(MultiDeclaration)lambdex.signature.params;
			declarations.addAll(mD.declarations);
		}

		declarations.forEach(d->{
			params.add(new Parameter(d.getName(), d.getType(), d.getModifiers()));
		});

	}







}
