package com.luxlunaris.cincia.backend.interpreter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.callables.CinciaMethod;
import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.CinciaClass;
import com.luxlunaris.cincia.backend.interfaces.CinciaIterable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.iterables.CinciaDict;
import com.luxlunaris.cincia.backend.iterables.CinciaList;
import com.luxlunaris.cincia.backend.iterables.DestructuredList;
import com.luxlunaris.cincia.backend.object.CinciaCinciaClass;
//import com.luxlunaris.cincia.backend.object.CinciaInterface;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.object.JavaClass;
import com.luxlunaris.cincia.backend.primitives.CinciaInt;
import com.luxlunaris.cincia.backend.primitives.CinciaKeyword;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
import com.luxlunaris.cincia.backend.stdlib.Stdlib;
import com.luxlunaris.cincia.backend.throwables.CinciaException;
import com.luxlunaris.cincia.backend.throwables.TypeError;
import com.luxlunaris.cincia.backend.types.TypeWrapper;
import com.luxlunaris.cincia.frontend.Compiler;
import com.luxlunaris.cincia.frontend.ast.declarations.FunctionDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.MultiDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.SingleDeclaration;
import com.luxlunaris.cincia.frontend.ast.declarations.VariableDeclaration;
import com.luxlunaris.cincia.frontend.ast.expressions.MatchExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.PipeExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.RangeExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.TernaryExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AndExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.ComparisonExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.MulExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.OrExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.forexp.ForExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.forexp.Generator;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictComprehension;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListComprehension;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ListExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.CalledExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.DotExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.IndexedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.postfix.ReassignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.primary.BracketedExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.type.PrimitiveType;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.DestructuringExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.MinusExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.unary.NegationExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Ast;
import com.luxlunaris.cincia.frontend.ast.interfaces.BinaryExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Declaration;
import com.luxlunaris.cincia.frontend.ast.interfaces.PostfixExpression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Statement;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;
import com.luxlunaris.cincia.frontend.ast.statements.CompoundStatement;
import com.luxlunaris.cincia.frontend.ast.statements.ImportStatement;
import com.luxlunaris.cincia.frontend.ast.statements.exception.CatchClause;
import com.luxlunaris.cincia.frontend.ast.statements.exception.ThrowStatement;
import com.luxlunaris.cincia.frontend.ast.statements.exception.TryStatement;
import com.luxlunaris.cincia.frontend.ast.statements.iteration.WhileStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.BreakStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ContinueStatement;
import com.luxlunaris.cincia.frontend.ast.statements.jump.ReturnStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.CaseStatement;
import com.luxlunaris.cincia.frontend.ast.statements.labelled.DefaultStatement;
import com.luxlunaris.cincia.frontend.ast.statements.selection.IfStatement;
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Bool;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Float;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Int;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.Str;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;
import com.luxlunaris.cincia.backend.interfaces.Stateful;



public class Interpreter extends AbstractTraversal<CinciaObject> {


	@Override
	public CinciaObject evalInt(Int intex, Enviro enviro) {
		return CinciaObject.wrap(intex.getValue());
	}

	@Override
	public CinciaObject evalFloat(Float floatex, Enviro enviro) {
		return CinciaObject.wrap(floatex.getValue());
	}

	@Override
	public CinciaObject evalStr(Str strex, Enviro enviro) {
		return CinciaObject.wrap(strex.getValue());
	}

	@Override
	public CinciaObject evalBool(Bool boolex, Enviro enviro) {
		return CinciaObject.wrap(boolex.getValue());
	}

	@Override
	public CinciaObject evalIdentifier(Identifier identex, Enviro enviro) {
		return enviro.get(identex.value);
	}

	@Override
	public CinciaObject evalTernaryExpression(TernaryExpression terex, Enviro enviro) {

		if(eval(terex.cond, enviro).__bool__().toJava()) {
			return eval(terex.thenExpression, enviro);
		}else {
			return eval(terex.elseExpression, enviro);
		}

	}

	@Override
	public CinciaObject evalIfStatement(IfStatement ifStatement, Enviro enviro) {

		if(eval(ifStatement.cond, enviro).__bool__().toJava()) {
			return eval(ifStatement.thenBlock, enviro);
		}else {
			return eval(ifStatement.elseBlock, enviro);
		}

	}


	@Override
	public CinciaObject evalBreakStatement(BreakStatement breakStatement, Enviro enviro) {
		return new CinciaKeyword(Keywords.BREAK);
	}

	@Override
	public CinciaObject evalContinueStatement(ContinueStatement continueStatement, Enviro enviro) {
		return new CinciaKeyword(Keywords.CONTINUE);
	}

	@Override
	public CinciaObject evalForExpression(ForExpression forS, Enviro enviro) {


		//TODO: filter condition doesn't work with unpacked variables after first variable

		List<Entry<CinciaIterable, Generator>> generators = forS.generators.stream().map(g ->    Map.entry(((CinciaIterable)eval(g.iterable, enviro) ),  g)   ).collect(Collectors.toList());
		List<CinciaIterable> iterables = generators.stream().map(  e->e.getKey().filter((PureCinciaFunction)  eval(LambdaExpression.fromExpression(e.getValue().loopVars.get(0), e.getValue().filter, Type.Any) , enviro)) ).collect(Collectors.toList()) ;
		List<List<Identifier>> loopVars = forS.generators.stream().map(g -> g.loopVars).collect(Collectors.toList());
		CinciaIterable shortest = iterables.stream().sorted( (i1,i2)-> (int) ( i1.size() - i2.size()) ).findFirst().get();
		ArrayList<CinciaObject> yielded = new ArrayList<CinciaObject>();


		for (int i=0; i < shortest.size(); i++) {

			// set the loop vars for this iteration
			for(int j=0; j< iterables.size(); j++) {

				CinciaObject o = iterables.get(j).get(i);

				if(loopVars.get(j).size()>1 && o instanceof CinciaIterable) {

					CinciaIterable itx = (CinciaIterable)o;

					if(loopVars.get(j).size() < itx.size()) {
						throw new RuntimeException("Too few loop vars!");
					}

					if(loopVars.get(j).size() > itx.size() + 1) {
						throw new RuntimeException("Too many loop vars!");
					}

					for(int v=0; v<itx.size(); v++) {
						enviro.set(loopVars.get(j).get(v).value, itx.get(v));
					}


				}else {
					// if o isn't an iterable, or there's just one loop var, don't unpack
					enviro.set(loopVars.get(j).get(0).value, o);
				}

			}

			// run this iteration	
			CinciaObject o = null;
			if(forS.block!=null){
				o = eval(forS.block, enviro);				
			}

			// add results to yield
			if(forS.yield!=null) {
				yielded.add(  eval(forS.yield, enviro) );
			}

			//check iteration exit value to determine what to do next
			if(o == null) {
				continue;
			}

			if(o.toJava().equals(Keywords.CONTINUE)) {
				continue;
			}

			if(o.toJava().equals(Keywords.BREAK)) {
				break;
			}

			//return statement
			if(o != null) { 
				return o;
			}

		}

		return new CinciaList(yielded);
	}

	@Override
	public CinciaObject evalWhileStatement(WhileStatement whileStatement, Enviro enviro) {

		while(eval(whileStatement.cond, enviro).__bool__().toJava()) {

			// run one iteration
			CinciaObject o = eval(whileStatement.block, enviro); 

			//check iteration exit value to determine what to do next
			if(o == null) {
				continue;
			}

			if(o.toJava().equals(Keywords.CONTINUE)) {
				continue;
			}

			if(o.toJava().equals(Keywords.BREAK)) {
				break;
			}

			//return statement
			if(o != null) { 
				return o;
			}

		}

		return null;
	}


	@Override
	public CinciaObject evalImportStatement(ImportStatement importStatement, Enviro enviro) {

		//TODO: refactor and standardize the behaviour of different kinds of imports!

		//from Java standard lib
		try {

			Class clazz =  Interpreter.class.getClassLoader().loadClass(importStatement.fromPath.value);
			Identifier id = (Identifier)importStatement.imports.get(0).getKey();			
			enviro.set( id.value, new JavaClass(clazz));
			return null;
		} catch (ClassNotFoundException e1) {

		}

		// from Cincia standard lib
		if(importStatement.fromPath.value.split("\\.")[0].equals(Stdlib.STDLIB)) {

			CinciaObject importedObj;

			if(importStatement.fromPath.value.split("\\.").length==1) {
				importedObj =  new Stdlib();
			}else {
				importedObj = new Stdlib().get(importStatement.fromPath.value);
			}

			Entry<PostfixExpression, Identifier> importEntry = importStatement.imports.get(0);
			String alias = importEntry.getValue().value;


			if(alias != Identifier.NULL.value) {
				enviro.set(alias, importedObj);
			}else if(importEntry.getKey() instanceof Identifier){
				enviro.set( ((Identifier)importEntry.getKey()).value , importedObj);
			}

			return null;

		}

		// from Cincia source file ...

		// ... create a new isolated env
		Enviro envCopy = enviro.newChild();

		String source = "";		

		try {
			//TODO: read relative-path import from source-file in a different directory than the cincia.jar correctly
			//TODO: store import directory in enviro for nested imports to resolve relative path

			String workingDir="";

			try {
				workingDir = ((CinciaString)envCopy.get(Enviro.WORKING_DIR)).toJava() + "/";
			} catch (RuntimeException e) {

			}

			//TODO: if path is absolute DONT' prepend...

			var importPath = workingDir+importStatement.fromPath.value;
			List<String> lines = Files.readAllLines(Paths.get(importPath), StandardCharsets.UTF_8);
			source = lines.stream().reduce((l1,l2)->l1+"\n"+l2).get();

			//if relative import path contains directories, append the directories to the current source file dir

			var relativeImportPath = importStatement.fromPath.value;
			var parts = Arrays.asList(relativeImportPath.split("/"));
			var folders = parts.subList(0, parts.size()-1);

			var deeper= folders.stream().reduce((a,b)->a+"/"+b).orElse("");

			if(folders.size()>0) {
				var newSourceFileDir = workingDir+deeper;
				envCopy.set(Enviro.WORKING_DIR, new CinciaString(newSourceFileDir ));
			}

		} catch (IOException e) {
			throw new RuntimeException("Wrong import!");//TODO: make class.
		}

		// ... evaluate the source-code in the new isolated env 
		List<Ast> statements = new Compiler().compile(source);

		try {
			statements.forEach(s -> eval(s, envCopy) );			
		} catch (Exception e) {
			// TODO: may catch redefinition (double import)
		}


		// ... import the desired pieces of the module into the current env	
		importStatement.imports.forEach(i->{

			CinciaObject desired = eval(i.getKey(), envCopy);
			String alias = i.getValue().value;

			if(alias != Identifier.NULL.value) {
				enviro.set(alias, desired);
			}else if(i.getKey() instanceof DotExpression){
				DotExpression dotExpression = (DotExpression)i.getKey();
				enviro.set(dotExpression.right.value, desired);
			}else if(i.getKey() instanceof Identifier){
				enviro.set(((Identifier)i.getKey()).value, desired);
			}

		});

		return null;

	}

	@Override
	public CinciaObject evalCompoundStatement(CompoundStatement cS, Enviro enviro) {

		for (Statement s : cS.statements) {

			CinciaObject o = eval(s, enviro);

			if (s instanceof ReturnStatement) {
				return o;
			}

			if (o!=null && o.toJava().equals(Keywords.BREAK) ) {
				return o;
			}

			if (o!=null && o.toJava().equals(Keywords.CONTINUE) ) {
				return o;
			}

		}

		return null;
	}


	@Override
	public CinciaObject evalTryStatement(TryStatement tryStatement, Enviro enviro) {

		try {
			eval(tryStatement.tryBlock, enviro);
		} catch (CinciaException e) { 

			for(CatchClause c :  tryStatement.catchClausesList) {

				SingleDeclaration sD = ((SingleDeclaration)c.throwable);
				String name = sD.getName();
				Type type = sD.getType();

				if(e.matches(type)) {
					enviro.set(name, e);
					eval(c.block, enviro);
					return null;
				}

				//TODO: finally
			}

			throw e;
		}

		return null;
	}

	@Override
	public CinciaObject evalThrowStatement(ThrowStatement throwStatement, Enviro enviro) {


		CinciaObject throwable = eval(throwStatement.throwable, enviro);

		if(throwable instanceof CinciaException) {
			throw (CinciaException)throwable;
		}else {
			// wrap non-throwable in a throwable
			CinciaException exception = new CinciaException();
			exception.set("value", throwable); //TODO: extract name
			throw exception;
		}

	}

	@Override
	public CinciaObject evalMatchExpression(MatchExpression mS, Enviro enviro) {

		//TODO: very important, else you risk nullpointer:
		//TODO add coverage test, or return a default val!

		CinciaObject condition = eval(mS.cond, enviro);

		for(CaseStatement c : mS.casesList  ) {

			try {

				// TODO: type matching with declarations
				//				if (c.cond instanceof Declaration) {
				//					System.out.println("declaration!");
				//				}

				// comparison expression: just check if it's true
				if (c.cond instanceof BinaryExpression && eval(c.cond, enviro).__bool__().toJava()) {
					return c.block!=null? eval(c.block, enviro) : eval(c.expression, enviro);
				}

				// a value/object, check equality
				if(eval(c.cond, enviro).__eq__(condition).__bool__().toJava()) {
					return c.block!=null? eval(c.block, enviro) : eval(c.expression, enviro);
				}


			}catch (RuntimeException e) {
				//				e.printStackTrace();
			}

		}

		return null;
	}

	@Override
	public CinciaObject evalDefaultStatement(DefaultStatement defaultStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalCaseStatement(CaseStatement caseStatement, Enviro enviro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject evalReturnStatement(ReturnStatement returnStatement, Enviro enviro) {
		return eval(returnStatement.expression, enviro);
	}


	/**
	 * Retutns a CinciaList with an element for each single expression in the multi expression.
	 */
	@Override
	public CinciaObject evalMultiExpression(MultiExpression multex, Enviro enviro) {

		List<CinciaObject> elems = multex.expressions
				.stream()
				.map(e-> eval(e, enviro)) 
				.flatMap( e -> e instanceof DestructuredList? ((DestructuredList)e).getList().stream() : Stream.of(e) ) // flatten out values to be unpacked
				.collect(Collectors.toList());

		return new CinciaList(elems); 
	}

	@Override
	public CinciaObject evalRangeExpression(RangeExpression rangex, Enviro enviro) {

		CinciaObject from = eval(rangex.from, enviro);
		CinciaObject to = eval(rangex.to, enviro);

		CinciaList cinciaList = new CinciaList(new PrimitiveType(PrimitiveType.INT));

		if(from instanceof CinciaInt && to instanceof CinciaInt) {

			for(int i=(int)from.toJava(); i<=(int)to.toJava(); i++) {
				cinciaList.add(new CinciaInt(i));
			}

		}

		return cinciaList;
	}

	@Override
	public CinciaObject evalFunctionDeclaration(FunctionDeclaration fD, Enviro enviro) {


		Type type =  (Type) eval(fD.signature, enviro);

		enviro.set(fD.name.value, null, type, fD.modifiers); 
		return null;
	}

	@Override
	public CinciaObject evalVariableDeclaration(VariableDeclaration vD, Enviro enviro) {

		Type type =  (Type) eval(vD.type, enviro);

		enviro.set(vD.name.value, null, type, vD.modifiers); 
		return null;
	}

	@Override
	public CinciaObject evalMultiDeclaration(MultiDeclaration mD, Enviro enviro) {

		for(Declaration d : mD.declarations) {
			evalDeclaration(d, enviro);
		}

		return null;
	}


	@Override
	public CinciaObject evalMulExpression(MulExpression mulex, Enviro enviro) {

		CinciaObject left = eval(mulex.left, enviro);
		CinciaObject right = eval(mulex.right, enviro);

		if(mulex.op == Operators.ASTERISK) {
			return left.__mul__(right);
		}else if(mulex.op == Operators.DIV) {
			return left.__div__(right);
		}else if(mulex.op == Operators.MOD) {
			return left.__mod__(right);
		}

		throw new RuntimeException("Unknown multiplication operator!");
	}

	@Override
	public CinciaObject evalAddExpression(AddExpression addex, Enviro enviro) {

		CinciaObject left = eval(addex.left, enviro);
		CinciaObject right = eval(addex.right, enviro);


		if(addex.op == Operators.PLUS) {
			return left.__add__(right);
		}else if(addex.op == Operators.MINUS) {
			return left.__sub__(right);
		}

		throw new RuntimeException("Unknown addition operator!");

	}

	@Override
	public CinciaObject evalComparisonExpression(ComparisonExpression compex, Enviro enviro) {

		CinciaObject left = eval(compex.left, enviro);
		CinciaObject right = eval(compex.right, enviro);

		switch (compex.op) {

		case COMPARE:
			return left.__eq__(right);
		case NE:
			return left.__ne__(right);
		case LT:
			return left.__lt__(right);
		case GT:
			return left.__gt__(right);
		case LTE:
			return left.__lte__(right);
		case GTE:
			return left.__gte__(right);
		default:
			throw new RuntimeException("Unknown comparison operator!");
		}

	}

	@Override
	public CinciaObject evalOrExpression(OrExpression orex, Enviro enviro) {
		CinciaObject left = eval(orex.left, enviro);
		CinciaObject right = eval(orex.right, enviro);
		return left.__or__(right);
	}

	@Override
	public CinciaObject evalAndExpression(AndExpression andex, Enviro enviro) {
		CinciaObject left = eval(andex.left, enviro);
		CinciaObject right = eval(andex.right, enviro);
		return left.__and__(right);
	}

	@Override
	public CinciaObject evalAssignmentExpression(AssignmentExpression assex, Enviro enviro) {

		CinciaObject rval = eval(assex.right, enviro);
		rval.setDocstring(assex.comment);
		CinciaObject key = null;
		Stateful container = null;
		String containerName = null;

		// if l-value is an identifier
		if(assex.left instanceof Identifier) {
			key = new CinciaString(((Identifier)assex.left).value);
			container = enviro;
			containerName = "";
		}

		// if l-value is contained in a dot expression
		try {
			DotExpression dotex = (DotExpression)assex.left;
			key = new CinciaString(dotex.right.value);
			container = eval(dotex.left, enviro);
			containerName = dotex.left.toString();
		}catch (ClassCastException e) {

		}

		// if l-value is contained in an indexed expresson 
		try {
			IndexedExpression indexex = (IndexedExpression)assex.left;
			key = eval(indexex.index, enviro);
			container = eval(indexex.indexable, enviro);
			containerName = indexex.indexable.toString();
		}catch (ClassCastException e) {

		}

		
//	TODO	assex.modifiers
		// set 
		try {
			container.set(key, rval); //TODO: also set modifiers
		} catch (TypeError e) {
			e.lvalue = key+" on "+containerName;
			e.expected =  e.expected==null? container.getType(key+"") : e.expected;
			e.got =  e.expected==null? rval.getType() : e.got; 
			throw e;
		}

		return rval;
	}

	@Override
	public CinciaObject evalClassExpression(ClassExpression classex, Enviro enviro) {

		CinciaCinciaClass c = new CinciaCinciaClass();

		for(Declaration dec : classex.declarations) {	
			eval(dec, c.getEnviro());
		}

		for(AssignmentExpression assign : classex.assignments) {
			eval(assign, c.getEnviro());
		}

		return c;
	}

	@Override
	public CinciaObject evalDictExpression(DictExpression dictex, Enviro enviro) {

		CinciaDict d = new CinciaDict(Type.Any, Type.Any);

		dictex.entries.forEach(e->{

			CinciaObject key = eval(e.getKey(), enviro);
			CinciaObject val = eval(e.getValue(), enviro);

			if(key instanceof CinciaString) {
				d.set(((CinciaString)key).toJava(), val);
			}

			if(key instanceof CinciaInt) {
				d.set(((CinciaInt)key).toJava(), val);
			}

		});

		//TODO: destructuring 

		return d;
	}

	@Override
	public CinciaObject evalDictComprehension(DictComprehension dictcompex, Enviro enviro) {

		CinciaIterable iterable = (CinciaIterable)eval(dictcompex.iterable, enviro);

		PureCinciaFunction mapKey = (PureCinciaFunction) eval(LambdaExpression.fromExpression((Identifier)dictcompex.source, dictcompex.key, Type.Any), enviro);
		PureCinciaFunction mapValue = (PureCinciaFunction) eval(LambdaExpression.fromExpression((Identifier)dictcompex.source, dictcompex.val, Type.Any), enviro);
		PureCinciaFunction filter = (PureCinciaFunction) eval(LambdaExpression.fromExpression((Identifier)dictcompex.source, dictcompex.where, new PrimitiveType(PrimitiveType.BOOL)), enviro);

		CinciaIterable filtered = iterable.filter(filter);
		CinciaIterable keys = filtered.map(mapKey);
		CinciaIterable vals = filtered.map(mapValue);

		CinciaDict result = new CinciaDict(Type.Any, Type.Any); //TODO: specify types

		for(int i=0; i < keys.size(); i++) {

			if(keys.get(i) instanceof CinciaString) {
				result.set(((CinciaString)keys.get(i)).toJava(), vals.get(i));
			}

			if(keys.get(i) instanceof CinciaInt) {
				result.set(((CinciaInt)keys.get(i)).toJava(), vals.get(i));
			}

		}

		return result;
	}

	@Override
	public CinciaObject evalLambdaExpression(LambdaExpression lambdex, Enviro enviro) {

		//TODO
		lambdex.signature = lambdex.signature.resolve(this::eval, enviro);


		try {
			// Check if env belongs to class, in that case return a method.
			CinciaCinciaClass b = (CinciaCinciaClass)enviro.get(CinciaCinciaClass.CLASS);


			return new CinciaMethod(lambdex, this::eval);			
		} catch (Exception e) {

		}

		// ... else make a function w/ factory method
		return CinciaFunction.make(lambdex, this::eval);

	}

	@Override
	public CinciaObject evalListComprehension(ListComprehension listcompex, Enviro enviro) {

		PureCinciaFunction map = (PureCinciaFunction) eval(LambdaExpression.fromExpression((Identifier)listcompex.source, listcompex.element, Type.Any), enviro);
		PureCinciaFunction filter = (PureCinciaFunction) eval(LambdaExpression.fromExpression((Identifier)listcompex.source, listcompex.where, new PrimitiveType(PrimitiveType.BOOL)), enviro);
		CinciaIterable iterable = (CinciaIterable)eval(listcompex.iterable, enviro);
		return iterable.filter(filter).map(map);

	}

	@Override
	public CinciaObject evalListExpression(ListExpression listex, Enviro enviro) {

		// get object (or objects in the form of a CinciaList) to be placed in new list
		CinciaObject o = eval(listex.elements, enviro);

		// object is a list of size one
		if(o instanceof CinciaList && ((CinciaList)o).size()==1) {
			return new CinciaList(Arrays.asList(o));
		}

		// multiple objects, just return the list already
		if(o instanceof CinciaList) {
			return o;
		}

		// object is a single destrutured element
		if(o instanceof DestructuredList) {
			return new CinciaList( ((DestructuredList)o).getList() );
		}

		// object is a single element
		return new CinciaList(Arrays.asList(o));

	}

	@Override
	public CinciaObject evalCalledExpression(CalledExpression callex, Enviro enviro) {

		// get args
		List<CinciaObject> args;
		CinciaObject o = eval(callex.args, enviro);	

		if(callex.args instanceof MultiExpression) { // multiple args
			args = ((CinciaList)o).getList();
		}else { // one single arg
			args = o==null? Arrays.asList() : Arrays.asList(o) ;
		}

		// get called expression
		CinciaObject f = eval(callex.callable, enviro);

		// if class, call constructor and return reference to new object		
		try {
			CinciaClass c = (CinciaClass)f;
			return c.newInstance(args);
		}catch (ClassCastException e) {

		}

		// if method, call on parent object's ORIGINAL env
		try {
			CinciaMethod cm = (CinciaMethod)f;
			return cm.run(args);
		}catch (ClassCastException e) {

		}

		// if pure function, run with args as ONLY input, don't let it even read the global env.
		try {
			PureCinciaFunction cm = (PureCinciaFunction)f;
			return cm.run(args);
		}catch (ClassCastException e) {

		}

		// else it's a top level function that reads from ext scope, call on COPY of whatever environment was passed in		
		try {
			CinciaFunction l = (CinciaFunction)f;
			return l.run(args, enviro.newChild());
		}catch (ClassCastException e) {

		}

		throw new RuntimeException("Unsupported callable type!");
	}

	@Override
	public CinciaObject evalDotExpression(DotExpression dotex, Enviro enviro) {

		try {
			CinciaObject dottable = eval(dotex.left, enviro);
			return dottable.get(dotex.right.value);					
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}


	@Override
	public CinciaObject evalIndexedExpression(IndexedExpression indexex, Enviro enviro) {
		CinciaObject o = eval(indexex.indexable, enviro);
		CinciaObject index = eval(indexex.index , enviro);
		return o.get(index);	
	}

	@Override
	public CinciaObject evalReassignmentExpression(ReassignmentExpression reassex, Enviro enviro) {


		AssignmentExpression assex = new AssignmentExpression();
		assex.left = reassex.left;

		switch(reassex.op) {
		case PLUSPLUS:
			assex.right = BinaryExpression.make(Operators.PLUS, assex.left, new Int(1));
			break;
		case MINUSMINUS:
			assex.right = BinaryExpression.make(Operators.MINUS, assex.left, new Int(1));
			break;
		case PLUS_ASSIGN:
			assex.right = BinaryExpression.make(Operators.PLUS, assex.left, reassex.right);
			break;
		case MINUS_ASSIGN:
			assex.right = BinaryExpression.make(Operators.MINUS, assex.left, reassex.right);
			break;
		case MUL_ASSIGN:
			assex.right = BinaryExpression.make(Operators.ASTERISK, assex.left, reassex.right);
			break;
		case DIV_ASSIGN:
			assex.right = BinaryExpression.make(Operators.DIV, assex.left, reassex.right);
			break;
		case MOD_ASSIGN:
			assex.right = BinaryExpression.make(Operators.MOD, assex.left, reassex.right);
			break;

		default:
			throw new RuntimeException("No such reassignment expression!");
		}

		return eval(assex, enviro);

	}

	@Override
	public CinciaObject evalBracketedExpression(BracketedExpression brackex, Enviro enviro) {
		return eval(brackex.expression, enviro);
	}

	@Override
	public CinciaObject evalDestructuringExpression(DestructuringExpression destex, Enviro enviro) {

		// TODO: if dict evaluate to list of lists 
		return new DestructuredList((CinciaList)eval(destex.arg, enviro));
	}

	@Override
	public CinciaObject evalMinusExpression(MinusExpression minex, Enviro enviro) {
		return eval(minex.arg, enviro).__neg__();
	}

	@Override
	public CinciaObject evalNegationExpression(NegationExpression negex, Enviro enviro) {
		return eval(negex.arg, enviro).__not__();
	}


	@Override
	public CinciaObject evalPipeExpression(PipeExpression pipex, Enviro enviro) {

		Enviro envCopy =  enviro.newChild();
		CinciaObject arg = eval(pipex.expressions.get(0), envCopy);

		//TODO: can this be parallelized like in bash?
		for(int i=1; i<pipex.expressions.size(); i++) {

			CinciaFunction f = (CinciaFunction)eval(pipex.expressions.get(i), envCopy); 

			try {
				arg = ((PureCinciaFunction)f).run(Arrays.asList(arg));//, envCopy);
			} catch (ClassCastException e) {
				arg = f.run(Arrays.asList(arg), envCopy);
			}

		}

		return arg;
	}

	@Override
	public CinciaObject evalTypeExpression(Type type, Enviro enviro) {

		//TODO: implement resolve in collection types		
		return new TypeWrapper(type.resolve(this::eval, enviro));

	}


}
