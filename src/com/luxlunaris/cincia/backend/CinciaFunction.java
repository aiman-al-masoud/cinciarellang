package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.backend.Interpreter.Eval;
import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;

public class CinciaFunction extends CinciaObject implements Callable{
	
	@FunctionalInterface
	interface WrappedFunction{
		CinciaObject run(List<CinciaObject> args);
	}

	private CompoundStatement block;
	private Expression expression;	
	private List<Entry<String, ? extends Type>> params;
	private Eval eval;
	private WrappedFunction wrappedFunction;

	public CinciaFunction(LambdaExpression lambdex, Eval eval) {
		super(lambdex.signature);
		this.expression = lambdex.expression;
		this.block = lambdex.block;
		this.eval = eval;
		parseParams();
	}
	
	public CinciaFunction(WrappedFunction wrappedFunction) {
		super(new IdentifierType("WrappedFunction"));
		this.wrappedFunction = wrappedFunction;
	}

	public CinciaObject run(List<CinciaObject> args, Enviro enviro) {

		if(args !=null) {

			// bind args to env, TODO: check matching types
			for(int i=0; i < args.size(); i++) {
				enviro.set(params.get(i).getKey(), args.get(i), params.get(i).getValue());
			}

		}

		if(block != null) {
			return eval.eval(this.block, enviro);
		}else if(expression != null){
			return eval.eval(this.expression, enviro);
		}else if(wrappedFunction != null){
			return wrappedFunction.run(args);
		}

		throw new RuntimeException("Lambda without expression nor block!");
	}

	public void parseParams() {

		Declaration decParams = ((Signature)type).params;
		List<Declaration> declarations = new ArrayList<Declaration>();

		try {
			SingleDeclaration sD =(SingleDeclaration)decParams;
			declarations.add(sD);
		}catch (ClassCastException e) {
			MultiDeclaration mD =(MultiDeclaration)decParams;
			declarations.addAll(mD.declarations);
		}


		params = declarations.stream().map(d->{

			if (d instanceof FunctionDeclaration) {
				FunctionDeclaration fD = (FunctionDeclaration)d;
				return Map.entry(fD.name.value, fD.signature);
			}

			if (d instanceof VariableDeclaration) {
				VariableDeclaration vD = (VariableDeclaration)d;
				return Map.entry(vD.name.value, vD.type);
			}

			return null;

		}).collect(Collectors.toList());
	}







}
