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
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.luxlunaris.cincia.backend.callables.CinciaFunction;
import com.luxlunaris.cincia.backend.callables.PureCinciaFunction;
import com.luxlunaris.cincia.backend.interfaces.Callable;
import com.luxlunaris.cincia.backend.interfaces.CinciaIterable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.iterables.CinciaDict;
import com.luxlunaris.cincia.backend.iterables.CinciaList;
import com.luxlunaris.cincia.backend.iterables.DestructuredList;
import com.luxlunaris.cincia.backend.object.CinciaCinciaClass;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.object.JavaClass;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.primitives.CinciaFloat;
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
import com.luxlunaris.cincia.frontend.ast.expressions.IfExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.MatchExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.MultiExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.PipeExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.RangeExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AddExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AndExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.AssignmentExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.ComparisonExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.MulExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.binary.OrExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.forexp.ForExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.forexp.Generator;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.ClassExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.DictExpression;
import com.luxlunaris.cincia.frontend.ast.expressions.objects.LambdaExpression;
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
import com.luxlunaris.cincia.frontend.ast.tokens.Identifier;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.BoolToken;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.FloatToken;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.IntToken;
import com.luxlunaris.cincia.frontend.ast.tokens.constant.StrToken;
import com.luxlunaris.cincia.frontend.ast.tokens.keyword.Keywords;
import com.luxlunaris.cincia.frontend.ast.tokens.operator.Operators;
import com.luxlunaris.cincia.backend.interfaces.Stateful;


public class Interpreter extends AbstractTraversal<CinciaObject> {

	@Override
	public CinciaObject evalInt(IntToken intex, Enviro enviro) {
		return CinciaObject.wrap(intex.getValue());
	}

	@Override
	public CinciaObject evalFloat(FloatToken floatex, Enviro enviro) {
		return CinciaObject.wrap(floatex.getValue());
	}

	@Override
	public CinciaObject evalStr(StrToken strex, Enviro enviro) {
		return CinciaObject.wrap(strex.getValue());
	}

	@Override
	public CinciaObject evalBool(BoolToken boolex, Enviro enviro) {
		return CinciaObject.wrap(boolex.getValue());
	}

	@Override
	public CinciaObject evalIdentifier(Identifier identex, Enviro enviro) {
		return enviro.get(identex.value);
	}

	@Override
	public CinciaObject evalIfExpression(IfExpression ifStatement, Enviro enviro) {

		if(eval(ifStatement.cond, enviro).__bool__().toJava()) {
			return eval(ifStatement.getThen(), enviro);
		}else {
			return eval(ifStatement.getElse(), enviro);
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

		// remove loop vars from external env
		loopVars.forEach(l->l.forEach(v->{			
			enviro.remove(v.value);
		}));

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
		Enviro envCopy = enviro.shallowCopy();

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
				envCopy.set(Enviro.WORKING_DIR, CinciaObject.wrap(newSourceFileDir));
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
				//TODO: type matching with declarations?

				// comparison expression: just check if it's true
				if (c.cond instanceof BinaryExpression && eval(c.cond, enviro).__bool__().toJava()) {
					return c.block!=null? eval(c.block, enviro) : eval(c.expression, enviro);
				}

				// a value/object, check equality
				if(eval(c.cond, enviro).__eq__(condition).__bool__().toJava()) {
					return c.block!=null? eval(c.block, enviro) : eval(c.expression, enviro);
				}

			}catch (RuntimeException e) {

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

		if(from instanceof CinciaInt && to instanceof CinciaInt) {

			List<CinciaObject> list = IntStream.range((int)from.toJava(), (int)to.toJava() + 1)
					.mapToObj(e->CinciaObject.wrap(e))
					.collect(Collectors.toList());
			return new CinciaList(list);
		}

		throw new RuntimeException("Invalid range!");
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
			key = CinciaObject.wrap(((Identifier)assex.left).value);
			container = enviro;
			containerName = "";
		}

		// if l-value is contained in a dot expression
		try {
			DotExpression dotex = (DotExpression)assex.left;
			key = CinciaObject.wrap(dotex.right.value);
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


		try {
			container.set(key, rval); //TODO: also set modifiers
		} catch (TypeError e) {
			e.lvalue = containerName+"."+key;
			e.expected = e.expected == null ? container.getType(key+"") : e.expected;
			e.got = e.got == null ? rval.getType() : e.got; 
			throw e;
		}

		return rval;
	}


	@Override
	public CinciaObject evalDictExpression(DictExpression dictex, Enviro enviro) {

		CinciaDict d = new CinciaDict(Type.Any, Type.Any);

		dictex.entries.forEach(e->{

			CinciaObject key = eval(e.getKey(), enviro);
			CinciaObject val = eval(e.getValue(), enviro);
			d.set(key, val);

		});

		//TODO: destructuring 

		return d;
	}


	@Override
	public CinciaObject evalClassExpression(ClassExpression classex, Enviro enviro) {

		CinciaCinciaClass c = new CinciaCinciaClass();
		c.setParent(enviro); //set the parent scope of the class, ie: the static/lexical context that surrounds the class, from which the class can reference variables

		for(Declaration dec : classex.declarations) {	
			eval(dec, c); //removed getEnviro() since class is an cinciaobject which is an enviro
		}

		for(AssignmentExpression assign : classex.assignments) {
			eval(assign, c);
		}

		return c;
	}

	@Override
	public CinciaObject evalLambdaExpression(LambdaExpression lambdex, Enviro enviro) {

		lambdex.signature = lambdex.signature.resolve(this::eval, enviro); // resolve any custom nested types in signature
		return CinciaFunction.make(lambdex, this::eval); // make a function w/ factory method
		// TODO: function.setParent(enviro) 

	}

	@Override
	public CinciaObject evalListExpression(ListExpression listex, Enviro enviro) {

		List<CinciaObject> list = listex.toList().stream().flatMap(e->{

			CinciaObject object = eval(e, enviro);

			if(object instanceof DestructuredList) {
				return ((DestructuredList)object).getList().stream();
			}else {
				return Stream.of(object);
			}

		}).collect(Collectors.toList());

		return new CinciaList(list);

	}

	@Override
	public CinciaObject evalCalledExpression(CalledExpression callex, Enviro enviro) {

		//		System.out.println(callex.args.toList());

		// get args // TODO: eval destructured lists
		//		var args = callex.args.toList()
		var args = callex.args
				.stream()
				.map(e->eval(e, enviro))
				.collect(Collectors.toList());


		try {
			// get callable expression ...
			Callable callable = (Callable) eval(callex.callable, enviro);
			return callable.run(args, enviro.shallowCopy()); // ... call it
		} catch (ClassCastException e) {
			throw new TypeError("can't call non-callable object!");
		}

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
			assex.right = BinaryExpression.make(Operators.PLUS, assex.left, new IntToken(1));
			break;
		case MINUSMINUS:
			assex.right = BinaryExpression.make(Operators.MINUS, assex.left, new IntToken(1));
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

		Enviro envCopy =  enviro.shallowCopy();
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

		//		System.out.println("evalTypeExp(): "+type.getClass());

		if(type instanceof PrimitiveType && ((PrimitiveType)type).value == Keywords.INT) {
			return CinciaInt.myClass;
		}

		if(type instanceof PrimitiveType && ((PrimitiveType)type).value == Keywords.BOOL) {
			return CinciaBool.myClass;
		}

		if(type instanceof PrimitiveType && ((PrimitiveType)type).value == Keywords.FLOAT) {
			return CinciaFloat.myClass;
		}

		if(type instanceof PrimitiveType && ((PrimitiveType)type).value == Keywords.STRING) {
			return CinciaString.myClass;
		}

		//TODO: implement resolve in collection types		
		return new TypeWrapper(type.resolve(this::eval, enviro));

	}


}
