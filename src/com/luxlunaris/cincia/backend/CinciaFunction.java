package com.luxlunaris.cincia.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.type.Signature;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;

public class CinciaFunction extends CinciaObject implements Callable{

	@FunctionalInterface
	interface Eval{
		Object eval(Ast ast, Enviro enviro);
	}

	private CompoundStatement block;
	private Expression expression;	
	List<Entry<String, ? extends Type>> params;

	public CinciaFunction(Signature signature, CompoundStatement block) {
		super(signature);
		this.block = block;
		parseParams();
	}

	public CinciaFunction(Signature signature, Expression expression) {
		super(signature);
		this.expression = expression;
	}

	public CinciaObject run(List<Expression> args, Enviro enviro, Eval eval) {

		// bind args to env
		for(int i=0; i < args.size(); i++) {
			Object o = eval.eval(args.get(i), enviro);
			enviro.set(params.get(i).getKey(), (CinciaObject)o, type);
		}
		
		
		if(block !=null) {
			return (CinciaObject) eval.eval(this.block, enviro);
		}else {
			return (CinciaObject) eval.eval(this.expression, enviro);
		}
		
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
