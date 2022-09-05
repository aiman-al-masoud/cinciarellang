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
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.tokens.modifier.Modifiers;

public class CinciaFunction extends AbstractCinciaObject implements Callable{

	@FunctionalInterface
	interface WrappedFunction{
		CinciaObject run(List<CinciaObject> args);
	}

	protected LambdaExpression lambdex;
	protected List<Entry<String, Type>> paramTypes;
	protected List<Entry<String, List<Modifiers>>> paramMods;
	protected Eval eval;
	protected WrappedFunction wrappedFunction;

	public CinciaFunction(LambdaExpression lambdex, Eval eval) {
		super(lambdex.signature);
		this.eval = eval;
		this.lambdex = lambdex;
		paramTypes = new ArrayList<Map.Entry<String, Type>>();
		paramMods = new ArrayList<Map.Entry<String,List<Modifiers>>>();
		parseParams();
	}

	public CinciaFunction(WrappedFunction wrappedFunction) {
		super(new IdentifierType("WrappedFunction")); //TODO: extract Id, NativeType
		this.wrappedFunction = wrappedFunction;
	}

	public CinciaObject run(List<CinciaObject> args, Enviro enviro) {


		if(args != null && wrappedFunction ==null) {

			// bind args to env, TODO: check matching types
			for(int i=0; i < args.size(); i++) {
				enviro.set(paramTypes.get(i).getKey(), args.get(i), paramTypes.get(i).getValue());
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
			paramTypes.add(Map.entry(d.getName(), d.getType()));
			paramMods.add(Map.entry(d.getName(), d.getModifiers()));
		});

	}







}
